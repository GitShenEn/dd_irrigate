package cn.dreamdeck.controller;



import cn.dreamdeck.model.dto.ChartDto;
import cn.dreamdeck.model.entity.DdDataSoilMoisture;
import cn.dreamdeck.service.DdDataSoilMoistureService;
import cn.dreamdeck.service.DdDataWaterMeterService;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("data")
@Api(value = "data", tags = "数据管理")
public class DdDataController {

    private DdDataWaterMeterService dataWaterMeterService;

    private DdDataSoilMoistureService moistureService;



    /**
     * 水表图表
     * @return
     */
    @ApiOperation(value = "水表图表", notes = "水表图表")
    @GetMapping("/getChart")
    public R getChart(ChartDto chartDto){
        if (StringUtils.isBlank(chartDto.getStartTime())){
            return  R.failed("开始时间不能为空");
        }
        if (StringUtils.isBlank(chartDto.getEndTime())){
            return R.failed("结束时间不能为空");
        }
        if (chartDto.getTimeType()==null){
            return  R.failed("时间类型不能为空");
        }
        return  R.ok(dataWaterMeterService.selectChart(chartDto));
    }


    /**
     * 用水量
     * @return
     */
    @ApiOperation(value = "用水量", notes = "用水量")
    @GetMapping("/getMeter")
    public R getMeter(Integer type){
        if (type==null){
            return  R.failed("查询类型不能为空（1:全部，2:上月）");
        }
        return  R.ok(dataWaterMeterService.selectMeter(type));
    }


    /**
     * 各项用水量
     * @return
     */
    @ApiOperation(value = "各项用水量", notes = "各项用水量")
    @GetMapping("/getWatering")
    public R getWatering(){
        return  R.ok(dataWaterMeterService.getWatering());
    }

    /**
     * 土壤墒情
     * @return
     */
    @ApiOperation(value = "土壤墒情", notes = "土壤墒情")
    @GetMapping("/getMoisture")
    public R getMoisture(Integer deviceId){
        if (deviceId==null){
            return  R.failed("设备ID不能为空");
        }
        return  R.ok(moistureService.list(Wrappers.<DdDataSoilMoisture>lambdaQuery()
        .eq(DdDataSoilMoisture::getWaterId,deviceId)));
    }











}
