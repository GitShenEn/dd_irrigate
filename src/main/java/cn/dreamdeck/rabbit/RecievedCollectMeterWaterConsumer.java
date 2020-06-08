package cn.dreamdeck.rabbit;

import cn.dreamdeck.model.CommonConstants;
import cn.dreamdeck.model.entity.DdDataWaterMeter;
import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.service.DdDeviceGatewayService;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = "collect-meter-water")
public class RecievedCollectMeterWaterConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RecievedCollectMeterWaterConsumer.class);

    @Resource
    private DdDeviceWaterService ddDeviceWaterService;
    @Resource
    private DdDeviceGatewayService gatewayService;

    @Autowired
    private RabbitProducer producer;

    /**
     * 消息消费 @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(DdDeviceWater deviceWater) { Integer loopNum = 0;
        while (true) if (loopNum >= 3) {
            deviceWater.setStatus("0");
            ddDeviceWaterService.updateById(deviceWater);
            break;
        } else {
            loopNum++;
            R result = ddDeviceWaterService.getRealData(deviceWater);
            if (result.getCode()==0){
                Float value = (Float) result.getData();
                DdDataWaterMeter ddDataWaterMeter = new DdDataWaterMeter();
                ddDataWaterMeter.setDeviceId(deviceWater.getDeviceId());
                ddDataWaterMeter.setWaterNum(value);
                R statusR = ddDeviceWaterService.status(deviceWater);
                if (statusR.getCode()==0){
                    String status =(String) statusR.getData();
                    deviceWater.setStatus(CommonConstants.STATUS_ON);
                    deviceWater.setIsOpen(status);
                    producer.sendDataMeterWater(ddDataWaterMeter);
                    break;
                }
            }else{
                logger.error("连接失败》》》---第" + loopNum + "次》》》》" + deviceWater.getDeviceName());
            }
        }
    }
}