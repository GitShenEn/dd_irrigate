package cn.dreamdeck.rabbit;


import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.model.entity.DdDataWaterMeter;
import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.model.entity.DdDeviceWater;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RabbitProducer {

    @Resource
    private AmqpTemplate rabbitTemplate;


    //土壤
    public void sendCollectSensorSoil(DdDeviceSoil deviceSoil) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.COLLECTSENSORSOIL, deviceSoil);
    }

    public void sendDataSensorSoil(DdDataSensorSoil ddDataSensorSoil) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.DATASENSORSOIL, ddDataSensorSoil);
    }

    //水表
    public void sendCollectMeterWater(DdDeviceWater ddDeviceMeterWater) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.COLLECTWATERMETER, ddDeviceMeterWater);
    }
    public void sendDataMeterWater(DdDataWaterMeter ddDataWaterMeter) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.DATAMETERWATER, ddDataWaterMeter);
    }


}
