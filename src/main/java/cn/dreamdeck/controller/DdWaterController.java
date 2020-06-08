package cn.dreamdeck.controller;


import cn.dreamdeck.Thread.WaterCollectThread;
import cn.dreamdeck.model.dto.DeviceWaterDto;
import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("water")
@Api(value = "water", tags = "水表管理")
public class DdWaterController {

    private DdDeviceWaterService ddDeviceWaterService;

    /**
     * 水表分页列表
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "水表分页列表", notes = "水表分页列表")
    @GetMapping("/page")
    public R getGroupPage(Page page, Integer type) {
        return R.ok(ddDeviceWaterService.selectAllPage(page, type));
    }

    /**
     * 水表全部列表
     *
     * @param type (1:已经分组，2：未分组,null为全部)
     */
    @ApiOperation(value = "水表全部列表", notes = "水表全部列表")
    @GetMapping("/all")
    public R getGroupAll(Integer type) {
        return R.ok(ddDeviceWaterService.selectAll(type));
    }

    /**
     * 按照设备类型查询水表
     *
     * @param type
     */
    @ApiOperation(value = "设备类型查询水表", notes = "设备类型查询水表")
    @GetMapping("/getDevice")
    public R getSmart(Integer type) {
        return R.ok(ddDeviceWaterService.list(Wrappers.<DdDeviceWater>lambdaQuery()
                .eq(DdDeviceWater::getType, type)
                .eq(DdDeviceWater::getDelFlag, "0")));
    }


    /**
     * 水表详细信息
     *
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "水表详细信息", notes = "水表详细信息")
    @GetMapping("/getWaterData")
    public R getWaterData(Integer deviceId) {
        if (deviceId == null) {
            return R.failed("设备ID不能为空");
        }
        return R.ok(ddDeviceWaterService.getData(deviceId));
    }


    /**
     * 新增水表
     * @return
     * @param waterDto
     * @return
     */
    @ApiOperation(value = "新增水表", notes = "新增水表")
    public R save(@RequestBody DeviceWaterDto waterDto) {
        if (waterDto.getDeviceId() != null) {
            return R.failed("新增时不传ID");
        }
        if (StringUtils.isBlank(waterDto.getDeviceName())) {
            return R.failed("设备名称不能为空");
        }
        if (StringUtils.isBlank(waterDto.getType())) {
            return R.failed("设备类型不能为空");
        }
        if (StringUtils.isBlank(waterDto.getLongitude())) {
            return R.failed("设备经度不能为空");
        }
        if (StringUtils.isBlank(waterDto.getLatitude())) {
            return R.failed("设备经度不能为空");
        }
        if (waterDto.getConnectPort() == null) {
            return R.failed("连接端口号");
        }
        if (waterDto.getSubsetId() == null) {
            return R.failed("设备编号不能为空");
        }
        if (waterDto.getGatewayId() == null) {
            return R.failed("网关ID不能为空");
        }
        List list = ddDeviceWaterService.list(Wrappers.<DdDeviceWater>lambdaQuery()
                .eq(DdDeviceWater::getDeviceName, waterDto.getDeviceName()));
        if (list != null && list.size() > 0) {
            return R.failed("水表名称不能重复");
        }
        List sublist = ddDeviceWaterService.list(Wrappers.<DdDeviceWater>lambdaQuery()
                .eq(DdDeviceWater::getSubsetId, waterDto.getSubsetId()));
        if (sublist != null && sublist.size() > 0) {
            return R.failed("设备编号不能重复");
        }
        return R.ok(ddDeviceWaterService.save(waterDto));
    }


    /**
     * 修改水表信息
     * @return
     */
    @ApiOperation(value = "修改水表信息", notes = "修改水表信息")
    @PostMapping("/update")
    public R update(@RequestBody DeviceWaterDto waterDto) {
        if (waterDto.getDeviceId() == null) {
            return R.failed("ID不能为空");
        }
        if (!StringUtils.isBlank(waterDto.getDeviceName())) {
            List list = ddDeviceWaterService.list(Wrappers.<DdDeviceWater>lambdaQuery()
                    .eq(DdDeviceWater::getDeviceName, waterDto.getDeviceName()));
            if (list != null && list.size() > 0) {
                return R.failed("水表名称不能重复");
            }
        }
        if (waterDto.getSubsetId() != null) {
            List list = ddDeviceWaterService.list(Wrappers.<DdDeviceWater>lambdaQuery()
                    .eq(DdDeviceWater::getSubsetId, waterDto.getSubsetId()));
            if (list != null && list.size() > 0) {
                return R.failed("设备编号不能重复");
            }
        }
        return R.ok(ddDeviceWaterService.update(waterDto));
    }


    /**
     * 删除水表
     * @return
     */
    @ApiOperation(value = "删除水表", notes = "删除水表")
    @DeleteMapping("/del")
    public R del(Integer deviceId){
        if (deviceId ==null){
            R.failed("水表ID不能为空");
        }
        return R.ok(ddDeviceWaterService.del(deviceId));
    }


    /**
     * 水表数据采集
     *
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "水表数据采集", notes = "水表数据采集")
    @GetMapping("/collect/{deviceId}")
    public R collect(@PathVariable Integer deviceId) {
        if (deviceId == null) {
            return R.failed("设备ID不能为空");
        }
        WaterCollectThread thread = new WaterCollectThread(deviceId);
        thread.run();
        return R.ok("采集成功");
    }



    /**
     * 开启水表
     * @return
     */
    @ApiOperation(value = "开启水表", notes = "开启水表")
    @PostMapping("/open")
    public R open(Integer deviceId){
        if (deviceId ==null){
            R.failed("水表ID不能为空");
        }
        DdDeviceWater ddDeviceWater =  ddDeviceWaterService.getById(deviceId);
        if (ddDeviceWater==null){
            R.failed("没有此设备");
        }
        return R.ok(ddDeviceWaterService.open(ddDeviceWater));
    }


    /**
     * 关闭水表
     * @return
     */
    @ApiOperation(value = "关闭水表", notes = "关闭水表")
    @PostMapping("/close")
    public R close(Integer deviceId){
        if (deviceId ==null){
            R.failed("水表ID不能为空");
        }
       DdDeviceWater ddDeviceWater =  ddDeviceWaterService.getById(deviceId);
        if (ddDeviceWater==null){
            R.failed("没有此设备");
        }
        return R.ok(ddDeviceWaterService.close(ddDeviceWater));
    }


    /**
     * 设备状态分析
     * @return
     */
    @ApiOperation(value = "设备状态分析", notes = "设备状态分析")
    @GetMapping("/getByStatus")
    public R getByStatus(){
        return  R.ok(ddDeviceWaterService.getByStatus());
    }


}
