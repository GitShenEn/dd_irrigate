package cn.dreamdeck.mapper;


import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.model.vo.DdDeviceSoilVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DdDeviceSoilMapper extends BaseMapper<DdDeviceSoil> {

    List<DdDeviceSoilVo> getList();
}
