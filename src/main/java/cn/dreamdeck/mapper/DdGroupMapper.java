package cn.dreamdeck.mapper;


import cn.dreamdeck.model.entity.DdGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DdGroupMapper extends BaseMapper<DdGroup> {


    /**
     * 分页列表
     * @param page
     * @return
     */
    IPage selectAll(Page page, @Param("type") Integer type);

    List<DdGroup> selectAll(@Param("type") Integer type);
}
