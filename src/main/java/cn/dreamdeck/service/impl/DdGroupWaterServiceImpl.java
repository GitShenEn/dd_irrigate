package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.DdGroupWaterMapper;
import cn.dreamdeck.model.entity.DdGroupWater;
import cn.dreamdeck.model.vo.GroupWaterVo;
import cn.dreamdeck.service.DdGroupWaterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DdGroupWaterServiceImpl extends ServiceImpl<DdGroupWaterMapper, DdGroupWater> implements DdGroupWaterService {

    @Override
    public List<GroupWaterVo> selectByGroup(Integer groupId) {
        return this.baseMapper.selectByGroup(groupId);
    }

    @Override
    public Boolean save(Integer groupId, Integer deviceId) {
        DdGroupWater groupWater = new DdGroupWater();
        groupWater.setDeviceId(deviceId);
        groupWater.setGroupId(groupId);
        this.baseMapper.insert(groupWater);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(Integer ID) {
        this.baseMapper.deleteById(ID);
        return Boolean.TRUE;
    }

    @Override
    public int delByGroup(Integer groupId) {
        return this.baseMapper.delByGroup(groupId);
    }

    @Override
    public DdGroupWater selectByDev(Integer deviceId) {
        return this.baseMapper.selectByDev(deviceId);
    }

}
