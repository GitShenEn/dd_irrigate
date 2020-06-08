package cn.dreamdeck.Thread;

import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.rabbit.RabbitProducer;
import cn.dreamdeck.service.impl.DdDeviceSoilServiceImpl;
import cn.dreamdeck.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class SoilCollectThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(SoilCollectThread.class);
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private DdDeviceSoilServiceImpl soilService = applicationContext.getBean(DdDeviceSoilServiceImpl.class);
    private RabbitProducer producer= applicationContext.getBean(RabbitProducer.class);
    Integer deviceId = null;

    public SoilCollectThread(Integer deviceId){
        this.deviceId=deviceId;
    }

    @Override
    public void run() {
        DdDeviceSoil soil =  soilService.getById(deviceId);
        if (soil!=null){
            logger.info(soil.getName()+">>>采集任务启动");
        }
        producer.sendCollectSensorSoil(soil);
    }
}
