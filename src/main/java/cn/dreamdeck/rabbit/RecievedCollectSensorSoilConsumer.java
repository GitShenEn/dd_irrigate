package cn.dreamdeck.rabbit;

import cn.dreamdeck.model.entity.DdCmd;
import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.model.entity.DdDeviceGateway;
import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.service.DdCmdService;
import cn.dreamdeck.service.DdDeviceGatewayService;
import cn.dreamdeck.service.DdDeviceSoilService;
import cn.dreamdeck.service.DdWarnSetService;
import cn.dreamdeck.utils.CheckUtil;
import cn.dreamdeck.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

@Component
@RabbitListener(queues = "collect-sensor-soil")
public class RecievedCollectSensorSoilConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RecievedCollectSensorSoilConsumer.class);
    @Resource
    private DdDeviceGatewayService gatewayService;
    @Resource
    private DdDeviceSoilService deviceSoilService;
    @Resource
    private DdCmdService cmdService;

    @Resource
    private RabbitProducer producer;

    @Resource
    private DdWarnSetService warnSetService;



    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(DdDeviceSoil ddDeviceSensorSoil) {
        DdDeviceGateway ddDeviceGateway = gatewayService.getById(ddDeviceSensorSoil.getGatewayId());
        if (ddDeviceGateway != null){
            //局域网网关
                if(ddDeviceGateway.getStatus().equals("1")){
                    Integer loopNum = 0;
                    while (true){
                        if (loopNum >= 3){
                            ddDeviceSensorSoil.setStatus("0");
                            deviceSoilService.updateById(ddDeviceSensorSoil);
                            break;
                        }else {
                            loopNum ++;
                            DdDataSensorSoil ddDataSensorSoil = new DdDataSensorSoil();
                            ddDataSensorSoil.setDeviceId(ddDeviceSensorSoil.getDeviceId());
                            List<DdCmd> ddDeviceSensorCmdList = cmdService.getByDeviceId(ddDeviceSensorSoil.getDeviceId());
                            for (int i = 0; i < ddDeviceSensorCmdList.size(); i++) {
                                try {
                                    byte[] baKeyWord = Util.hexStrToBinaryStr(ddDeviceSensorCmdList.get(i).getCmdData());
                                    Socket socket = new Socket(ddDeviceGateway.getIp(), ddDeviceSensorSoil.getConnectPort());
                                    //设置超时时间
                                    socket.setSoTimeout(1000);
                                    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
                                    os.write(baKeyWord);
                                    os.flush();
                                    Thread.sleep(100);

                                    byte[] prefixByte = new byte[11];

                                    socket.getInputStream().read(prefixByte);
                                    String prefixValue = Util.byte2hex(prefixByte);
                                    String result = CheckUtil.Make_CRC(prefixByte);
                                    byte[] crc = new byte[2];
                                    socket.getInputStream().read(crc);
                                    String crcvalue = CheckUtil.byteToHex(crc);
                                    if(result.equals(crcvalue)) {
                                        Integer subsetId = Integer.valueOf(Integer.valueOf(prefixValue.substring(0,2),16).toString());
                                        if (subsetId.equals( ddDeviceSensorSoil.getSubsetId())){
                                            long value =Long.parseLong(prefixValue.substring(ddDeviceSensorCmdList.get(i).getStart(), ddDeviceSensorCmdList.get(i).getEnd()), 16);
                                            String convertValue = "";
                                            if (ddDeviceSensorCmdList.get(i).getDecimal() > 0){
                                                if (ddDeviceSensorCmdList.get(i).getDecimal() == 1){
                                                    if(value>32768){
                                                        value=(value-65536);
                                                    }
                                                    convertValue =  String.valueOf((float) value / 10);
                                                }
                                                if (ddDeviceSensorCmdList.get(i).getDecimal() == 2){
                                                    convertValue =  String.valueOf((float) value / 100);
                                                }
                                            }else {
                                                convertValue = String.valueOf(Float.valueOf(String.valueOf(value)).longValue());
                                            }
                                            if (ddDeviceSensorCmdList.get(i).getCmdType() == 1){
                                                ddDataSensorSoil.setTemperature(Float.valueOf(convertValue));//温度
                                            }
                                            if (ddDeviceSensorCmdList.get(i).getCmdType() == 2){
                                                ddDataSensorSoil.setHumidity(Float.valueOf(convertValue));//湿度
                                                warnSetService.warnVerify(ddDeviceSensorSoil.getName(),ddDataSensorSoil.getDeviceId(),201002,Float.valueOf(convertValue),201);
                                            }
                                            if (ddDeviceSensorCmdList.get(i).getCmdType() == 3){
                                                ddDataSensorSoil.setSalinity(Float.valueOf(convertValue));//盐度
                                            }
                                        }else {
                                            Thread.sleep(100);
                                        }
                                    }else {
                                        Thread.sleep(100);
                                    }
                                    os.close();
                                    Thread.sleep(100);

                                } catch (Exception e){
                                    logger.error("连接失败》》》---第" + loopNum + "次》》》》" + ddDeviceSensorSoil.getDeviceId());
                                }
                            }
                            if (ddDataSensorSoil.getTemperature() != null
                                    && ddDataSensorSoil.getHumidity() != null
                                    && ddDataSensorSoil.getSalinity() != null){
                                ddDeviceSensorSoil.setStatus("1");
                                deviceSoilService.updateById(ddDeviceSensorSoil);
                                //保存土壤采集数据
                                producer.sendDataSensorSoil(ddDataSensorSoil);
                                break;
                            }
                        }
                    }
                }else {
                    ddDeviceSensorSoil.setStatus("0");
                    deviceSoilService.updateById(ddDeviceSensorSoil);
                }
            }
        }
}