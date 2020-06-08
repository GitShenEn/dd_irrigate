package cn.dreamdeck.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String COLLECTWATERMETER = "collect-meter-water";
    public static final String DATAMETERWATER = "data-meter-water";

    public static final String COLLECTSENSORSOIL = "collect-sensor-soil";
    public static final String DATASENSORSOIL = "data-sensor-soil";


    /**
     * 定义水表数据队列
     * @return
     */
    @Bean
    public Queue collectWaterMeter() {
        return new Queue(COLLECTWATERMETER);
    }
    @Bean
    public Queue dataMeterWater() {
        return new Queue(DATAMETERWATER);
    }


    /**
     * 定义土壤数据队列
     * @return
     */
    @Bean
    public Queue collectSensorSoil() {
        return new Queue(COLLECTSENSORSOIL);
    }
    @Bean
    public Queue dataSensorSoil() {
        return new Queue(DATASENSORSOIL);
    }


}
