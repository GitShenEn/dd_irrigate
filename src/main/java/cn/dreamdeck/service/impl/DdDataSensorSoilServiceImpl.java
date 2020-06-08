package cn.dreamdeck.service.impl;


import cn.dreamdeck.mapper.DdDataSensorSoilMapper;
import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.service.DdDataSensorSoilService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDataSensorSoilServiceImpl extends ServiceImpl<DdDataSensorSoilMapper, DdDataSensorSoil> implements DdDataSensorSoilService {
}
