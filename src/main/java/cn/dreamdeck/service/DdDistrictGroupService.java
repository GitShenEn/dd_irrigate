package cn.dreamdeck.service;

import cn.dreamdeck.model.entity.DdDistrictGroup;
import cn.dreamdeck.model.vo.DistrictGroupVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdDistrictGroupService  extends IService<DdDistrictGroup> {

    List<DistrictGroupVo> selectByDis(Integer districtId);

    /**
     * 按照分区删除
     * @param districtId
     * @return
     */
    int delByDis(Integer districtId);

    Boolean save(Integer districtId, Integer groupId);

    Boolean delById(Integer ID);

    DdDistrictGroup selectByGroup(Integer groupId);

    List<DdDistrictGroup> selectByRank(Integer districtId, Integer rank);

    Boolean setRank(Integer ID, Integer rank);


}
