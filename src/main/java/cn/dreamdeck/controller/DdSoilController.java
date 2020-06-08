package cn.dreamdeck.controller;


import cn.dreamdeck.Thread.SoilCollectThread;
import cn.dreamdeck.model.dto.DeviceSoilDto;
import cn.dreamdeck.service.DdDeviceSoilService;
import cn.dreamdeck.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("soil")
@Api(value = "soil", tags = "土壤管理")
public class DdSoilController {

    private DdDeviceSoilService deviceSoilService;


    /**
     * 单个设备土壤墒情
     * @return
     */
    @ApiOperation(value = "所有土壤设备", notes = "所有土壤设备")
    @GetMapping("/list")
    public R list(){
       // return  R.ok(deviceSoilService.list(Wrappers.<DdDeviceSoil>lambdaQuery().eq(DdDeviceSoil::getDelFlag,"0")));
        return  R.ok(deviceSoilService.getList());
    }


    /**
     * 新增土壤传感器
     *
     * @param soilDto
     * @return
     */
    @ApiOperation(value = "新增土壤传感器", notes = "新增土壤传感器")
    @PostMapping("/save")
    public R save(DeviceSoilDto soilDto){
        if (StringUtils.isBlank(soilDto.getName())){
            return R.failed("设备名称不能为空");
        }
        if (StringUtils.isBlank(soilDto.getLatitude())){
            return R.failed("纬度不能为空");
        }
        if (StringUtils.isBlank(soilDto.getLongitude())){
            return R.failed("经度不能为空");
        }
        if (soilDto.getSubsetId()==null){
            return R.failed("设备编号不能为空");
        }
        if (soilDto.getConnectBaud()==null){
            return R.failed("波特率不能为空");
        }
        if (soilDto.getConnectPort()==null){
            return R.failed("连接端口号不能为空");
        }
        if (soilDto.getGatewayId()==null){
            return R.failed("网关ID不能为空");
        }
        return R.ok(deviceSoilService.save(soilDto));
    }


    /**
     * 删除土壤传感器
     *
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "删除土壤传感器", notes = "删除土壤传感器")
    @DeleteMapping("/del")
    public R del(Integer  deviceId){
        if (deviceId==null){
            return R.failed("设备ID不能为空");
        }
        return R.ok(deviceSoilService.del(deviceId));
    }

    /**
     * 单个设备土壤墒情
     * @return
     */
    @ApiOperation(value = "单个设备土壤墒情", notes = "单个设备土壤墒情")
    @GetMapping("/getById")
    public R getById(Integer deviceId){
        if (deviceId==null){
            return  R.failed("水表ID不能为空");
        }
        return  R.ok(deviceSoilService.getDataById(deviceId));
    }


    /**
     * 按照水表查询土壤墒情
     * @return
     */
    @ApiOperation(value = "按照水表查询土壤墒情", notes = "按照水表查询土壤墒情")
    @GetMapping("/getByWaterId")
    public R getByWaterId(Integer deviceId){
        if (deviceId==null){
            return  R.failed("设备ID不能为空");
        }
        return  R.ok(deviceSoilService.getDataByWaterId(deviceId));
    }



    /**
     * 土壤采集
     *
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "土壤采集", notes = "土壤采集")
    @GetMapping("/collect/{deviceId}")
    public R collect(@PathVariable Integer deviceId){
        if (deviceId==null){
            return R.failed("设备ID不能为空");
        }
        SoilCollectThread thread = new SoilCollectThread(deviceId);
        thread.run();
        return R.ok("采集成功");
    }

}
