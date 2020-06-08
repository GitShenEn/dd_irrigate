package cn.dreamdeck.service.impl;


import cn.dreamdeck.mapper.DdCmdMapper;
import cn.dreamdeck.model.entity.DdCmd;
import cn.dreamdeck.service.DdCmdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DdCmdServiceImpl extends ServiceImpl<DdCmdMapper, DdCmd> implements DdCmdService {
    @Override
    public List<DdCmd> getByDeviceId(Integer deviceId) {
        return this.baseMapper.getByDeviceId(deviceId);
    }
}
