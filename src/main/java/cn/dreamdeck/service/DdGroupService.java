package cn.dreamdeck.service;


import cn.dreamdeck.model.entity.DdGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdGroupService extends IService<DdGroup> {

    IPage getGroupPage(Page page, Integer type);

    List<DdGroup> selectAll(Integer type);

    Boolean saveGroup(String groupName);

    Boolean editGroup(Integer groupId, String groupName);

    Boolean deleteById(Integer groupId);
}
