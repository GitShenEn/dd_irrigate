package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.DdDistrictGroupMapper;
import cn.dreamdeck.model.entity.DdDistrictGroup;
import cn.dreamdeck.model.vo.DistrictGroupVo;
import cn.dreamdeck.service.DdDistrictGroupService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DdDistrictGroupServiceImpl extends ServiceImpl<DdDistrictGroupMapper, DdDistrictGroup> implements DdDistrictGroupService {

    @Override
    public List<DistrictGroupVo> selectByDis(Integer districtId) {
        return this.baseMapper.selectByDis(districtId);
    }

    @Override
    public int delByDis(Integer districtId) {
        return this.baseMapper.delByDis(districtId);
    }

    @Override
    public Boolean save(Integer districtId, Integer groupId) {

        DdDistrictGroup districtGroup = new DdDistrictGroup();
        districtGroup.setDistrictId(districtId);
        districtGroup.setGroupId(groupId);
        List<DdDistrictGroup> list = this.list(Wrappers.<DdDistrictGroup>lambdaQuery().eq(DdDistrictGroup::getDistrictId, districtId));
        if (list!=null&&list.size()>0){
            districtGroup.setRank(list.size()+1);
        }else{
            districtGroup.setRank(1);
        }
        this.baseMapper.insert(districtGroup);
        return Boolean.TRUE;
    }

    @Override
    public Boolean delById(Integer ID) {
        DdDistrictGroup ddDistrictGroup =  this.baseMapper.selectById(ID);
        List<DdDistrictGroup> list = this.baseMapper.selectByRank(ddDistrictGroup.getDistrictId(), ddDistrictGroup.getRank());
        for (DdDistrictGroup districtGroup :list){
            districtGroup.setRank(districtGroup.getRank()-1);
            this.baseMapper.updateById(districtGroup);
        }
        this.baseMapper.deleteById(ID);
        return Boolean.TRUE;
    }

    @Override
    public DdDistrictGroup selectByGroup(Integer groupId) {
        return this.baseMapper.selectByGroup(groupId);
    }

    @Override
    public List<DdDistrictGroup> selectByRank(Integer districtId, Integer rank) {
        return this.baseMapper.selectByRank(districtId,rank);
    }

    @Override
    public Boolean setRank(Integer ID, Integer rank) {
        DdDistrictGroup districtGroup = this.baseMapper.selectById(ID);
        List<DdDistrictGroup> list = this.list(Wrappers.<DdDistrictGroup>lambdaQuery()
                .eq(DdDistrictGroup::getDistrictId, districtGroup.getDistrictId())
                .eq(DdDistrictGroup::getRank, rank));
        for (DdDistrictGroup ddDistrictGroup:list){
            ddDistrictGroup.setRank(districtGroup.getRank());
            this.baseMapper.updateById(ddDistrictGroup);
        }
        districtGroup.setRank(rank);
        this.baseMapper.updateById(districtGroup);
        return Boolean.TRUE;
    }


}
