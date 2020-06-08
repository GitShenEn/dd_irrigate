package cn.dreamdeck.Thread;

import cn.dreamdeck.model.entity.DdStrategy;
import cn.dreamdeck.model.vo.DistrictGroupVo;
import cn.dreamdeck.model.vo.FailedVo;
import cn.dreamdeck.model.vo.JobVo;
import cn.dreamdeck.service.impl.DdDeviceWaterServiceImpl;
import cn.dreamdeck.service.impl.DdDistrictGroupServiceImpl;
import cn.dreamdeck.service.impl.DdStrategyServiceImpl;
import cn.dreamdeck.utils.DateUtil;
import cn.dreamdeck.utils.SpringUtils;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class IrrigationThread extends Thread {
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private DdStrategyServiceImpl strategyService = applicationContext.getBean(DdStrategyServiceImpl.class);
    private DdDeviceWaterServiceImpl waterService = applicationContext.getBean(DdDeviceWaterServiceImpl.class);
    private DdDistrictGroupServiceImpl districtGroupService = applicationContext.getBean(DdDistrictGroupServiceImpl.class);
    List<FailedVo> openFailed = new ArrayList<>();
    List<FailedVo> closeFailed = new ArrayList<>();
    JobVo jobVo = new JobVo();
    Integer strategyId = null;

    public IrrigationThread(Integer strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public void run() {
        DdStrategy strategy = strategyService.getById(strategyId);
        if (!DateUtil.belongCalendar(strategy.getStartYtd(), strategy.getEndYtd())){
            return;
        }
        Integer openTime = strategy.getOpenTime();
        jobVo.setStrategyId(strategy.getStrategyId());
        jobVo.setEndHms(strategy.getEndHms());
        jobVo.setJobName(strategy.getStrategyName());
        List<DistrictGroupVo> groupVos = districtGroupService.selectByDis(strategy.getDistrictId());
        for (DistrictGroupVo vo : groupVos) {
            Integer groupId = vo.getGroupId();
            if (waterService.hasKey(strategyId)) {
                waterService.closeJob(strategyId);
            }
            jobVo.setGroupId(groupId);
            openFailed = waterService.groupOpen(groupId, openFailed);
            for (int i = 0; i < openTime * 6; i++) {
                try {
                    if (waterService.hasKey(strategyId)){ break;};
                    jobVo.setThisNum((openTime * 6 - i) / 6);
                    waterService.setJobMsg(jobVo);
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            closeFailed = waterService.groupClose(groupId, closeFailed);
        }
        waterService.closeJob(strategyId);
    }
}
