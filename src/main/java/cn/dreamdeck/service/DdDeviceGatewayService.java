package cn.dreamdeck.service;


import cn.dreamdeck.model.dto.DeviceGatewayDto;
import cn.dreamdeck.model.entity.DdDeviceGateway;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceGatewayService extends IService<DdDeviceGateway> {
    boolean save(DeviceGatewayDto gatewayDto);

    String saveCollectJob(DdDeviceGateway gateway);

    String delCollectJob(DdDeviceGateway gateway);

}
