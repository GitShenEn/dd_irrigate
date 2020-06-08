package cn.dreamdeck.mapper;

import cn.dreamdeck.model.dto.ChartDto;
import cn.dreamdeck.model.entity.DdDataWaterMeter;
import cn.dreamdeck.model.vo.ChartVO;
import cn.dreamdeck.model.vo.WaterMeterVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DdDataWaterMeterMapper extends BaseMapper<DdDataWaterMeter> {

    List<ChartVO> selectChart(@Param("chartDto") ChartDto chartDto);

    List<WaterMeterVO> selectMeter(@Param("time") String time, @Param("type") Integer type);

    Float selectNum(@Param("deviceId") Integer deviceId, @Param("time") String time);
}
