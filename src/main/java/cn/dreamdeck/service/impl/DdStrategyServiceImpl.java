package cn.dreamdeck.service.impl;


import cn.dreamdeck.mapper.DdStrategyMapper;
import cn.dreamdeck.model.CommonConstants;
import cn.dreamdeck.model.dto.StrategyDto;
import cn.dreamdeck.model.entity.DdStrategy;

import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.model.vo.StrategyVo;
import cn.dreamdeck.service.DdStrategyService;
import cn.dreamdeck.utils.CronUtil;
import cn.dreamdeck.utils.DateUtil;
import cn.dreamdeck.utils.RedisUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DdStrategyServiceImpl extends ServiceImpl<DdStrategyMapper, DdStrategy> implements DdStrategyService {

    private RedisUtil redisUtil;


    @Override
    public List<StrategyVo> selectByDis(Integer districtId) {
        return this.baseMapper.selectByDis(districtId);
    }

    @Override
    public Boolean save(StrategyDto strategyDto) {
        DdStrategy strategy = new DdStrategy();
        BeanUtils.copyProperties(strategyDto, strategy);
        strategy.setDelFlag(CommonConstants.STATUS_NORMAL);
        strategy.setStatus(CommonConstants.CLOSE);  /*默认关闭*/
        if (strategyDto.getOpenTime() == null){
            strategyDto.setOpenTime(30);
        }
        if (strategyDto.getOpenInterval() == null){
            strategyDto.setOpenInterval(1);
        }
        this.baseMapper.insert(strategy);
        return Boolean.TRUE;
    }

    @Override
    public Boolean update(StrategyDto strategyDto) {
        DdStrategy strategy = this.baseMapper.selectById(strategyDto.getStrategyId());
        if (strategy!=null){
            this.close(strategyDto.getStrategyId());
            BeanUtils.copyProperties(strategyDto, strategy);
            strategy.setStatus(CommonConstants.CLOSE);
            this.baseMapper.updateById(strategy);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean del(Integer strategyId) {
        DdStrategy strategy =  this.baseMapper.selectById(strategyId);
        if (strategy!=null){
            this.close(strategyId);
            strategy.setStatus(CommonConstants.CLOSE);
            strategy.setDelFlag(CommonConstants.STATUS_DEL);
            this.baseMapper.updateById(strategy);
        }
        return Boolean.TRUE;
    }

    @Override
    public Map getTime(Integer size, String startH, String endH, Integer openTime) {
        double a = size;
        double hron = a * ((double) openTime / (double) 60.0);
        hron = hron + 0.17;
        String time = "";
        if (!StringUtils.isBlank(startH)) {
            time = DateUtil.addHourDate(startH, hron);
        }
        if (!StringUtils.isBlank(endH)) {
            time = DateUtil.addHourDate(endH, -hron);
        }
        Map map = new HashMap();
        map.put("time", time);
        map.put("hour", DateUtil.addHourDate("00:00", hron));
        return map;
    }

    @Override
    public String open(Integer strategyId) {
        DdStrategy strategy =  this.baseMapper.selectById(strategyId);
        if (strategy!=null){
            String cron  = CronUtil.getCron(strategy.getStartYtd(),strategy.getStartHms(),strategy.getOpenInterval());

//            SysJob startJob = new SysJob();
//            startJob.setJobId("irrigation_start_" +strategy.getStrategyId());
//            startJob.setJobName(strategy.getStrategyName());
//            startJob.setJobGroup("irrigation_start_" + strategy.getDistrictId());
//            startJob.setJobOrder("9");
//            startJob.setJobType("3");
//            startJob.setExecutePath("http://dd-gateway:9999/irrigation/strategy/start/" + strategyId);
//            startJob.setCronExpression(cron);
//            startJob.setMisfirePolicy("1");
//            startJob.setJobTenantType("2");
//            startJob.setCreateBy("irrigation");
//            remoteJobService.save(startJob, SecurityConstants.FROM);
//            remoteJobService.startJob(startJob.getJobId());
//            strategy.setStatus(CommonConstants.OPEN);
//            this.baseMapper.updateById(strategy);
        }
        return "开启成功";
    }

    @Override
    public String close(Integer strategyId) {
        DdStrategy strategy =  this.baseMapper.selectById(strategyId);
//        if (strategy!=null){
//            remoteJobService.shutdownJob("irrigation_start_"+strategy.getStrategyId());
//            remoteJobService.removeById("irrigation_start_"+strategy.getStrategyId());
//            if (redisUtil.hasKey(irrigationConstants.STRATEGY+strategyId)){
//                redisUtil.set(irrigationConstants.STATE+strategyId,false);
//            }
//            strategy.setStatus(CommonConstants.CLOSE);
//            this.baseMapper.updateById(strategy);
//        }
        return "关闭成功";
    }

    @Override
    public List getRunDate() {
        List resList = new ArrayList();
        List<DdStrategy> list = this.baseMapper.selectList(Wrappers.<DdStrategy>lambdaQuery()
        .eq(DdStrategy::getDelFlag,"0"));
        for (DdStrategy strategy:list){
            String key = irrigationConstants.STRATEGY+strategy.getStrategyId();
            if (redisUtil.hasKey(key)){
                resList.add(redisUtil.get(key));
            }
        }
        return resList;
    }


}
