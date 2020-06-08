package cn.dreamdeck.mapper;


import cn.dreamdeck.model.entity.DdStrategy;
import cn.dreamdeck.model.vo.StrategyVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DdStrategyMapper extends BaseMapper<DdStrategy> {

    List<StrategyVo> selectByDis(Integer districtId);
}
