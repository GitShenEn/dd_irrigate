package cn.dreamdeck.mapper;

import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.model.vo.WaterVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DdDeviceWaterMapper extends BaseMapper<DdDeviceWater> {

    /**
     * 分页列表
     *
     * @param page
     * @return
     */
     IPage selectAll(Page page, @Param("type") Integer type);
    /**
     * 所有列表
     *
     * @param type (1:未分组，2：已经分组)
     * @return
     */
    List<WaterVo> selectAll(@Param("type") Integer type);

    /**
     * 查询设备的开关次数
     * @param deviceId
     * @param time
     * @return
     */
    Integer selectSwitch(@Param("deviceId") Integer deviceId, @Param("time") String time);





}
