package cn.dreamdeck.rabbit;


import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.service.DdDataSensorSoilService;
import cn.dreamdeck.utils.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@RabbitListener(queues = "data-sensor-soil")
public class RecievedDataSensorSoilConsumer {

    @Resource
    private DdDataSensorSoilService ddDataSensorSoilService;


    @Resource
    private RedisUtil redisUtil;

    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(DdDataSensorSoil ddDataSensorSoil) {

        if (ddDataSensorSoil!=null){
            ddDataSensorSoilService.save(ddDataSensorSoil);
            redisUtil.set(irrigationConstants.DATA_SOIL+ddDataSensorSoil.getDeviceId(),ddDataSensorSoil);
        }
    }

}