package cn.dreamdeck.service.impl;


import cn.dreamdeck.mapper.DdDeviceGatewayMapper;
import cn.dreamdeck.model.dto.DeviceGatewayDto;
import cn.dreamdeck.model.entity.DdDeviceGateway;
import cn.dreamdeck.service.DdDeviceGatewayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDeviceGatewayServiceImpl extends ServiceImpl<DdDeviceGatewayMapper, DdDeviceGateway> implements DdDeviceGatewayService {




    @Override
    public boolean save(DeviceGatewayDto gatewayDto) {
        DdDeviceGateway gateway = new DdDeviceGateway();
        BeanUtils.copyProperties(gatewayDto,gateway);
        gateway.setDelFlag("0");
        gateway.setStatus("0");
        this.baseMapper.insert(gateway);
        saveCollectJob(gateway);
        return true;
    }


    public String saveCollectJob(DdDeviceGateway gateway) {
//        if (gateway!=null){
//            delCollectJob(gateway);
//            SysJob startJob = new SysJob();
//            startJob.setJobId("collect_gateway_" +gateway.getDeviceId());
//            startJob.setJobName(gateway.getName()+"定时采集任务");
//            startJob.setJobGroup("collect_gateway_" + gateway.getDeviceId());
//            startJob.setJobOrder("9");
//            startJob.setJobType("3");
//            startJob.setExecutePath("http://dd-gateway:9999/irrigation/gateway/collect/" + gateway.getDeviceId());
//            startJob.setCronExpression("0 0/5 * * * ?");
//            startJob.setMisfirePolicy("1");
//            startJob.setJobTenantType("2");
//            startJob.setCreateBy("collectGateway");
//            remoteJobService.save(startJob, SecurityConstants.FROM);
//            remoteJobService.startJob(startJob.getJobId());
//            return "成功";
//        }
        return "失败";
    }

    @Override
    public String delCollectJob(DdDeviceGateway gateway) {
//        if (gateway!=null) {
//            //先删除
//            remoteJobService.shutdownJob("collect_gateway_" + gateway.getDeviceId());
//            remoteJobService.removeById("collect_gateway_" + gateway.getDeviceId());
//            return "成功";
//        }
        return "失败";
    }
}
