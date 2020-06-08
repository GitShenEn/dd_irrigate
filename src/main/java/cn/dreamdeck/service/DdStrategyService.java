package cn.dreamdeck.service;

import cn.dreamdeck.model.dto.StrategyDto;
import cn.dreamdeck.model.entity.DdStrategy;
import cn.dreamdeck.model.vo.StrategyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface DdStrategyService extends IService<DdStrategy> {

    List<StrategyVo> selectByDis(Integer districtId);

    Boolean save(StrategyDto strategyDto);

    Boolean update(StrategyDto strategyDto);

    Boolean del(Integer strategyId);

    Map  getTime(Integer size, String startH, String endH, Integer openTime);

    String open(Integer strategyId);

    String close(Integer strategyId);

    List getRunDate();
}
