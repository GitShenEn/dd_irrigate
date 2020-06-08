package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.DdWarnLogMapper;
import cn.dreamdeck.mapper.DdWarnSetMapper;
import cn.dreamdeck.model.entity.DdWarnLog;
import cn.dreamdeck.model.entity.DdWarnSet;
import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.service.DdWarnSetService;
import cn.dreamdeck.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Slf4j
@Service
@AllArgsConstructor
public class DdWarnSetServiceImpl extends ServiceImpl<DdWarnSetMapper, DdWarnSet> implements DdWarnSetService {


    private DdWarnLogMapper warnLogMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean warnVerify(String name,Integer deviceId, Integer type, Float value, Integer classify) {
        try {
            if (value==null){
                return false;
            }
            DdWarnSet deviceWarn = this.baseMapper.getByDeviceId(deviceId,type);
            if (deviceWarn!=null){
                   if (value>deviceWarn.getMaximum()||value<deviceWarn.getMinimum()){
                       this.setWaterWarn(name,deviceId,type,classify,value);
                  /*     WateringThread thread = new WateringThread(deviceId,deviceWarn.getMaximum(),deviceWarn.getMinimum());
                       thread.run();*/
                   }else{
                       this.delWaterWarn(deviceId,type);
                   }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean setWaterWarn(String name,Integer deviceId,Integer type,Integer classify,Float value) {
        if (!redisUtil.hasKey(irrigationConstants.WARN_KEY+deviceId+type)){
            Integer count =  warnLogMapper.getByDeviceStatus(deviceId,type);
            if (count==0){
                DdWarnLog saveLog = new DdWarnLog();
                saveLog.setDeviceId(deviceId);
                saveLog.setType(type);
                saveLog.setStatus(irrigationConstants.WARN_NO);
                saveLog.setValue(value);
                saveLog.setClassify(classify);
                warnLogMapper.insert(saveLog);
                redisUtil.set(irrigationConstants.WARN_KEY+deviceId+type,saveLog.getLogId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delWaterWarn(Integer deviceId,Integer type) {
        if (redisUtil.hasKey(irrigationConstants.WARN_KEY+deviceId+type)){
             Integer logId = (Integer)redisUtil.get(irrigationConstants.WARN_KEY+deviceId+type);
             DdWarnLog warnLog = warnLogMapper.selectById(logId);;
            if (warnLog!=null){
                if (warnLog.getStatus().equals(irrigationConstants.WARN_IN)){
                    warnLog.setStatus(irrigationConstants.WARN_MAN);
                }else{
                    warnLog.setStatus(irrigationConstants.WARN_SYS);
                    warnLog.setDisposePeople("自动处理");
                }
                warnLog.setDisposeTime(LocalDateTime.now());
                warnLogMapper.updateById(warnLog);
                redisUtil.del(irrigationConstants.WARN_KEY+deviceId+"_"+type);
                return true;
            }
        }
        return false;
    }


}
