package cn.dreamdeck.service.impl;


import cn.dreamdeck.mapper.DdDataWaterMeterMapper;
import cn.dreamdeck.model.dto.ChartDto;
import cn.dreamdeck.model.entity.DdDataWaterMeter;
import cn.dreamdeck.model.vo.ChartVO;
import cn.dreamdeck.model.vo.WaterMeterVO;
import cn.dreamdeck.service.DdDataWaterMeterService;
import cn.dreamdeck.utils.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DdDataWaterMeterServiceImpl extends ServiceImpl<DdDataWaterMeterMapper, DdDataWaterMeter> implements DdDataWaterMeterService {

    @Override
    public Map selectChart(ChartDto chartDto) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        Map map = new HashMap();
        List<ChartVO> contrastList = this.baseMapper.selectChart(chartDto);
            chartDto.setStartTime(chartDto.getEndTime());
        List<ChartVO> list = this.baseMapper.selectChart(chartDto);
        Double total = list.stream().collect(Collectors.summingDouble(ChartVO::getNum));
        Double contrastTotal = contrastList.stream().collect(Collectors.summingDouble(ChartVO::getNum));
        map.put("list",list);
        map.put("contrastList",contrastList);
        map.put("total", df.format(total));
        map.put("contrastTotal", df.format(contrastTotal));
        return map;
    }

    @Override
    public List<WaterMeterVO> selectMeter(Integer  type) {
        String time = DateUtil.getLastMonth();
        if (type==1){
            time=null;
        }
        List<WaterMeterVO> waterMeter = this.baseMapper.selectMeter(time,null);
        for (WaterMeterVO vo:waterMeter){
            vo.setEconomize(vo.getThisNum()*0.25);
            vo.setMoney(vo.getThisNum()*0.25*9.2);
        }
        if (waterMeter.size()==0){
            WaterMeterVO vo = new WaterMeterVO();
            vo.setType(type);
            vo.setThisNum(0.0);
            vo.setEconomize(0.0);
            vo.setMoney(0.0);
            waterMeter.add(vo);
        }
        return waterMeter;
    }

    @Override
    public Map getWatering() {
        Map map = new HashMap();
        map.put("smart",this.baseMapper.selectMeter( null,1));
        map.put("common",this.baseMapper.selectMeter( null,0));
        map.put("lastCommon",this.baseMapper.selectMeter(DateUtil.getLastMonth(),1));
        map.put("lastSmart",this.baseMapper.selectMeter( DateUtil.getLastMonth(),0));
        return map;
    }
}
