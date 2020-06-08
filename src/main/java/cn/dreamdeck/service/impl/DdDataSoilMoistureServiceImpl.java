package cn.dreamdeck.service.impl;


import cn.dreamdeck.mapper.DdDataSoilMoistureMapper;
import cn.dreamdeck.model.entity.DdDataSoilMoisture;
import cn.dreamdeck.service.DdDataSoilMoistureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDataSoilMoistureServiceImpl extends ServiceImpl<DdDataSoilMoistureMapper, DdDataSoilMoisture> implements DdDataSoilMoistureService {
}
