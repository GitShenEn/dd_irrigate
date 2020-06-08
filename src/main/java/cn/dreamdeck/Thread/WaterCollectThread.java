package cn.dreamdeck.Thread;

import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.rabbit.RabbitProducer;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class WaterCollectThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(SoilCollectThread.class);
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private DdDeviceWaterService waterService = applicationContext.getBean(DdDeviceWaterService.class);
    private RabbitProducer producer= applicationContext.getBean(RabbitProducer.class);

    Integer deviceId = null;

    public WaterCollectThread(Integer deviceId){
        this.deviceId=deviceId;
    }

    @Override
    public void run() {
        DdDeviceWater deviceWater =  waterService.getById(deviceId);
        if (deviceWater!=null){
            logger.info(deviceWater.getDeviceName()+">>>采集任务启动");
        }
        producer.sendCollectMeterWater(deviceWater);
    }
}
