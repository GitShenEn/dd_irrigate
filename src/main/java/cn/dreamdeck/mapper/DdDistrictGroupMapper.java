package cn.dreamdeck.mapper;

import cn.dreamdeck.model.entity.DdDistrictGroup;
import cn.dreamdeck.model.vo.DistrictGroupVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DdDistrictGroupMapper extends BaseMapper<DdDistrictGroup> {

    List<DistrictGroupVo> selectByDis(Integer districtId);

    int delByDis(Integer districtId);

    DdDistrictGroup selectByGroup(Integer groupId);


    List<DdDistrictGroup> selectByRank(@Param("districtId") Integer districtId, @Param("rank") Integer rank);

}
