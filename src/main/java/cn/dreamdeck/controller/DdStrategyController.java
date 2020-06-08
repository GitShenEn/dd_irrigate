package cn.dreamdeck.controller;

import cn.dreamdeck.Thread.IrrigationThread;
import cn.dreamdeck.model.dto.StrategyDto;
import cn.dreamdeck.model.entity.DdDistrict;
import cn.dreamdeck.model.entity.DdDistrictGroup;
import cn.dreamdeck.model.entity.DdStrategy;
import cn.dreamdeck.service.DdDistrictGroupService;
import cn.dreamdeck.service.DdDistrictService;
import cn.dreamdeck.service.DdStrategyService;
import cn.dreamdeck.utils.CronUtil;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("strategy")
@Api(value = "strategy", tags = "策略管理")
public class DdStrategyController {

    private DdStrategyService strategyService;

    private DdDistrictService districtService;

    private DdDistrictGroupService districtGroupService;

    /**
     * 策略查询
     *
     * @param districtId
     * @return
     */
    @ApiOperation(value = "查询策略", notes = "查询策略")
    @GetMapping("/getByDis")
    public R getByDis(Integer districtId) {
        if (districtId == null) {
            return R.failed("分区ID不能为空");
        }
        return R.ok(strategyService.selectByDis(districtId));
    }


    /**
     * 新增策略
     *
     * @param strategyDto
     * @return
     */
    @ApiOperation(value = "新增策略", notes = "新增策略")
    @PostMapping("/save")
    public R save(StrategyDto strategyDto) {
        if (strategyDto.getStrategyId()!=null) {
            return R.failed("新增时不传ID");
        }
        if (StringUtils.isBlank(strategyDto.getStrategyName())) {
            return R.failed("策略名称不能为空");
        }
        if (StringUtils.isBlank(strategyDto.getStartYtd())) {
            return R.failed("开始年月日不能为空");
        }
        if (StringUtils.isBlank(strategyDto.getEndYtd())) {
            return R.failed("结束年月日不能为空");
        }
        if (StringUtils.isBlank(strategyDto.getStartHms())) {
            return R.failed("开始时分秒不能为空");
        }
        if (StringUtils.isBlank(strategyDto.getEndHms())) {
            return R.failed("结束时分秒不能为空");
        }
        if (strategyDto.getDistrictId() == null) {
            return R.failed("分区不能为空");
        }
        DdDistrict ddDistrict = districtService.getById(strategyDto.getDistrictId());
        if (ddDistrict==null){
            return R.failed("分区不存在");
        }
        String cron  = CronUtil.getCron(strategyDto.getStartYtd(),strategyDto.getStartHms(),strategyDto.getOpenInterval());
        if (cron==null){
            return R.failed("时间格式不正确，策略无法执行");
        }
        List<DdStrategy> list = strategyService.list(Wrappers.<DdStrategy>lambdaQuery()
                .eq(DdStrategy::getDistrictId, strategyDto.getDistrictId())
                .eq(DdStrategy::getStrategyName, strategyDto.getStrategyName())
                .eq(DdStrategy::getDelFlag, "0"));
        if (list != null && list.size() > 0) {
            return R.failed("策略名不能重复");
        }
        return R.ok(strategyService.save(strategyDto));
    }

    /**
     * 修改策略
     *
     * @param strategyDto
     * @return
     */
    @ApiOperation(value = "修改策略", notes = "修改策略")
    @PostMapping("/update")
    public R update(StrategyDto strategyDto) {
        if (strategyDto.getStrategyId()==null) {
            return R.failed("策略ID不能为空");
        }
        DdStrategy strategy = strategyService.getById(strategyDto.getStrategyId());
        if (strategy==null){
            return R.failed("没有此策略");
        }
        return R.ok(strategyService.update(strategyDto));
    }



    /**
     * 删除策略
     *
     * @param strategyId
     * @return
     */
    @ApiOperation(value = "删除策略", notes = "删除策略")
    @PostMapping("/del")
    public R del(Integer strategyId){
        if (strategyId==null){
            return R.failed("策略ID不能为空");
        }
        return R.ok(strategyService.del(strategyId));
    }


    /**
     * 获取灌溉时间
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取灌溉时间", notes = "获取灌溉时间")
    @GetMapping("/getTime")
    public R getTime(Integer districtId, String startH, String endH, Integer openTime) {
        if (districtId==null) {
            return R.failed("分区ID不能为空");
        }
        if (startH == null && endH == null) {
            return R.failed("开始时间或结束时间不能为空");
        }
        List list = districtGroupService.list(Wrappers.<DdDistrictGroup>lambdaQuery()
                .eq(DdDistrictGroup::getDistrictId, districtId));
        if (list == null) {
            return R.failed("该分区下没有分组");
        }
        return R.ok( strategyService.getTime(list.size(),startH,endH,openTime));
    }


    /**
     * 开启策略
     *
     * @param
     * @return
     */
    @ApiOperation(value = "开启策略", notes = "开启策略")
    @PostMapping("/open")
    public R open(Integer strategyId){
        if (strategyId==null){
            return R.failed("策略ID不能为空");
        }
        DdStrategy strategy =  strategyService.getById(strategyId);
        if (strategy==null){
            return R.failed("策略不存在");
        }

        return R.ok(strategyService.open(strategyId));
    }

    /**
     * 关闭策略
     *
     * @param
     * @return
     */
    @ApiOperation(value = "关闭策略", notes = "关闭策略")
    @PostMapping("/close")
    public R close(Integer strategyId){
        if (strategyId==null){
            return R.failed("策略ID不能为空");
        }
        DdStrategy strategy =  strategyService.getById(strategyId);
        if (strategy==null){
            return R.failed("策略不存在");
        }
        return R.ok(strategyService.close(strategyId));
    }

    /**
     * 启动
     *
     * @return
     */
    @ApiOperation(value = "立即启动", notes = "立即启动")
    @GetMapping("/start/{strategyId}")
    public R start(@PathVariable Integer strategyId){
        IrrigationThread thread = new IrrigationThread(strategyId);
        thread.start();
        return R.ok("开始浇水");
    };



    /**
     * 获取执行策略信息
     *
     * @return
     */
    @ApiOperation(value = "获取执行策略信息", notes = "获取执行策略信息")
    @GetMapping("/getRunDate")
    public R getRunDate(){
        List list =  strategyService.getRunDate();
        if (list.size()==0){
            return R.failed("没有正在执行的策略");
        }
        return R.ok(list);
    };



}
