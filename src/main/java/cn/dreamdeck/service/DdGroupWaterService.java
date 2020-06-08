package cn.dreamdeck.service;



import cn.dreamdeck.model.entity.DdGroupWater;
import cn.dreamdeck.model.vo.GroupWaterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdGroupWaterService extends IService<DdGroupWater> {

    List<GroupWaterVo> selectByGroup(Integer groupId);

    Boolean save(Integer groupId, Integer deviceId);

    Boolean deleteById(Integer ID);

    /**
     * 按照组删除
     * @param groupId
     * @return
     */
    int delByGroup(Integer groupId);


    DdGroupWater selectByDev(Integer deviceId);
}
