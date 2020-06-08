package cn.dreamdeck.mapper;


import cn.dreamdeck.model.entity.DdGroupWater;
import cn.dreamdeck.model.vo.GroupWaterVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DdGroupWaterMapper  extends BaseMapper<DdGroupWater> {

    List<GroupWaterVo> selectByGroup(Integer groupId);

    int delByGroup(Integer groupId);

    DdGroupWater selectByDev(Integer deviceId);

}
