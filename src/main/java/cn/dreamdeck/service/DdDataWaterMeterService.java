package cn.dreamdeck.service;


import cn.dreamdeck.model.dto.ChartDto;
import cn.dreamdeck.model.entity.DdDataWaterMeter;
import cn.dreamdeck.model.vo.WaterMeterVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface DdDataWaterMeterService  extends IService<DdDataWaterMeter> {

    Map selectChart(ChartDto chartDto);

    List<WaterMeterVO> selectMeter(Integer type);

    Map getWatering();

}
