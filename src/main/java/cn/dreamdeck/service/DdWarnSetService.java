package cn.dreamdeck.service;


import cn.dreamdeck.model.entity.DdWarnSet;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdWarnSetService extends IService<DdWarnSet> {

    boolean warnVerify(String name, Integer deviceId, Integer type, Float value, Integer classify);

    boolean setWaterWarn(String name, Integer deviceId, Integer type, Integer classify, Float value);

    boolean delWaterWarn(Integer deviceId, Integer type);


}
