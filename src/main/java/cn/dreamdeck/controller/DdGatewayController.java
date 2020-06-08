package cn.dreamdeck.controller;


import cn.dreamdeck.Thread.GatewayCollectThread;
import cn.dreamdeck.model.dto.DeviceGatewayDto;
import cn.dreamdeck.service.DdDeviceGatewayService;
import cn.dreamdeck.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("gateway")
@Api(value = "gateway", tags = "网关管理")
public class DdGatewayController {

    DdDeviceGatewayService gatewayService;

    /**
     * 新增网关设备
     *
     * @param deviceGatewayDto
     * @return
     */
    @ApiOperation(value = "新增网关设备", notes = "新增网关设备")
    @PostMapping("/save")
    public R save(DeviceGatewayDto deviceGatewayDto){
        if (deviceGatewayDto.getDeviceId()!=null){
            R.failed("新增时不需要ID");
        }
        if (StringUtils.isBlank(deviceGatewayDto.getName())){
            R.failed("设备名称不能为空");
        }
        if (StringUtils.isBlank(deviceGatewayDto.getIp())){
            R.failed("设备IP不能为空");
        }
        if (StringUtils.isBlank(deviceGatewayDto.getLatitude())){
            return R.failed("纬度不能为空");
        }
        if (StringUtils.isBlank(deviceGatewayDto.getLongitude())){
            return R.failed("经度不能为空");
        }
        return R.ok(gatewayService.save(deviceGatewayDto));
    }



    /**
     * 网关监控
     *
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "网关监控", notes = "网关监控")
    @GetMapping("/collect/{deviceId}")
    public R collect(@PathVariable Integer deviceId){
        if (deviceId==null){
            return R.failed("设备ID不能为空");
        }
        GatewayCollectThread thread = new GatewayCollectThread(deviceId);
        thread.run();
        return R.ok("采集成功");
    }
}
