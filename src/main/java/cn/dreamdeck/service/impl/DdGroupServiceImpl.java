package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.DdGroupMapper;
import cn.dreamdeck.model.CommonConstants;
import cn.dreamdeck.model.entity.DdGroup;
import cn.dreamdeck.service.DdGroupService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DdGroupServiceImpl  extends ServiceImpl<DdGroupMapper, DdGroup> implements DdGroupService {


    @Override
    public IPage getGroupPage(Page page, Integer type) {
        return this.baseMapper.selectAll(page,type);
    }

    @Override
    public List<DdGroup> selectAll(Integer type) {
        return this.baseMapper.selectAll(type);
    }

    @Override
    public Boolean saveGroup(String groupName) {

        DdGroup ddGroup = new DdGroup();
        ddGroup.setName(groupName);
        ddGroup.setDelFlag(CommonConstants.STATUS_NORMAL);
        this.baseMapper.insert(ddGroup);

        return Boolean.TRUE;
    }

    @Override
    public Boolean editGroup(Integer groupId,String groupName) {

        DdGroup ddGroup = this.baseMapper.selectById(groupId);
        if (ddGroup != null){
            ddGroup.setName(groupName);
            this.updateById(ddGroup);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteById(Integer groupId) {
        DdGroup ddGroup = this.baseMapper.selectById(groupId);
        if (ddGroup != null){
            ddGroup.setDelFlag(CommonConstants.STATUS_DEL);
            this.updateById(ddGroup);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


}
