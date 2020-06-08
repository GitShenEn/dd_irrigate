package cn.dreamdeck.Thread;

import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.service.impl.DdDeviceSoilServiceImpl;
import cn.dreamdeck.utils.RedisUtil;
import cn.dreamdeck.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class WateringThread extends Thread  {

    private static final Logger logger = LoggerFactory.getLogger(SoilCollectThread.class);
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private DdDeviceWaterService waterService = applicationContext.getBean(DdDeviceWaterService.class);
    private DdDeviceSoilServiceImpl soilService = applicationContext.getBean(DdDeviceSoilServiceImpl.class);
    private RedisUtil redisUtil = applicationContext.getBean(RedisUtil.class);

    private Integer deviceId;
    private Integer max;
    private Integer min;


    public WateringThread(Integer deviceId, Integer max, Integer min){
        this.deviceId = deviceId;
        this.max = max;
        this.min = min;
    }

    @Override
    public void run() {
        if (!redisUtil.hasKey(irrigationConstants.DISPOSE+deviceId)){

            DdDeviceSoil deviceSoil = soilService.getById(deviceId);
            DdDeviceWater deviceWater = waterService.getById(deviceSoil.getWaterId());
            if (deviceWater!=null){
                redisUtil.set(irrigationConstants.DISPOSE+deviceId,deviceId);
                //正在执行的灌溉策略
                try {
                    waterService.open(deviceWater);
                    for (int i = 0;i<180;i++){
                        DdDataSensorSoil sensorSoil =  (DdDataSensorSoil)redisUtil.get(irrigationConstants.DATA_SOIL+deviceId);
                        if (sensorSoil.getHumidity()<max&&sensorSoil.getHumidity()>min){
                            break;
                        }
                        Thread.sleep(10000);
                    }
                    waterService.close(deviceWater);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        redisUtil.del(irrigationConstants.DISPOSE+deviceId);
    }
}
