package cn.dreamdeck.Thread;

import cn.dreamdeck.model.entity.DdDeviceGateway;
import cn.dreamdeck.service.impl.DdDeviceGatewayServiceImpl;
import cn.dreamdeck.utils.IpUtil;
import cn.dreamdeck.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class GatewayCollectThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(SoilCollectThread.class);
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private DdDeviceGatewayServiceImpl gatewayService = applicationContext.getBean(DdDeviceGatewayServiceImpl.class);
    Integer deviceId = null;

    public GatewayCollectThread(Integer deviceId){
        this.deviceId=deviceId;
    }

    @Override
    public void run() {
       DdDeviceGateway gateway =  gatewayService.getById(deviceId);
       if (gateway!=null){
           String status = "0";
           if(IpUtil.ping(gateway.getIp())){
               status = "1";
           }
           gateway.setStatus(status);
           gatewayService.updateById(gateway);
       }
    }
}
