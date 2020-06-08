package cn.dreamdeck.rabbit;

import cn.dreamdeck.model.CommonConstants;
import cn.dreamdeck.model.entity.DdDataWaterMeter;
import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.service.DdDataWaterMeterService;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.service.DdWarnSetService;
import cn.dreamdeck.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = "data-meter-water")
public class RecievedDataMeterWaterConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RecievedDataMeterWaterConsumer.class);
    @Resource
    private DdDataWaterMeterService ddDataWaterMeterService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private DdDeviceWaterService waterService;

    @Resource
    private DdWarnSetService warnSetService;



    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(DdDataWaterMeter ddDataWaterMeter) {
        logger.error(">>>>" + ddDataWaterMeter.getDeviceId());
        Integer deviceId = ddDataWaterMeter.getDeviceId();
        DdDataWaterMeter redisInfo = (DdDataWaterMeter)redisUtil.get(irrigationConstants.DATA_WATER+deviceId);
        if (redisInfo!=null){
            if(redisInfo.getWaterNum() != null) {
                DdDeviceWater deviceWater = waterService.getById(deviceId);
                if (ddDataWaterMeter.getWaterNum() - redisInfo.getWaterNum() > 0) {
                    ddDataWaterMeter.setThisNum(ddDataWaterMeter.getWaterNum() - redisInfo.getWaterNum());
                    ddDataWaterMeterService.save(ddDataWaterMeter);
                    redisUtil.set(irrigationConstants.DATA_WATER+deviceId,ddDataWaterMeter);
                    if (deviceWater.getIsOpen().equals(CommonConstants.IS_OPEN_NO)){
                        waterService.close(deviceWater);
                        warnSetService.setWaterWarn(deviceWater.getDeviceName(),deviceId,204001,204,0F);
                    }else{
                        warnSetService.delWaterWarn(deviceId,204001);
                    }
                }else {
                    if (deviceWater.getIsOpen().equals(CommonConstants.IS_OPEN_YES)){
                        warnSetService.setWaterWarn(deviceWater.getDeviceName(),deviceId,204002,204,0F);
                    }else{
                        warnSetService.delWaterWarn(deviceId,204002);
                    }
                }
            }
        }else{
            ddDataWaterMeter.setThisNum(0f);
            ddDataWaterMeterService.save(ddDataWaterMeter);
            redisUtil.set(irrigationConstants.DATA_WATER+deviceId,ddDataWaterMeter);
        }
    }
}