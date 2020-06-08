package cn.dreamdeck.service;


import cn.dreamdeck.model.dto.DeviceSoilDto;
import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.model.vo.DdDeviceSoilVo;
import cn.dreamdeck.model.vo.SoilDataVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdDeviceSoilService extends IService<DdDeviceSoil> {

    boolean save(DeviceSoilDto soilDto);

    boolean del(Integer deviceId);

    String saveCollectJob(DdDeviceSoil deviceSoil);

    String delCollectJob(DdDeviceSoil deviceSoil);

    SoilDataVo getDataById(Integer deviceId);

    DdDataSensorSoil getDataByWaterId(Integer deviceId);

    List<DdDeviceSoilVo> getList();
}
