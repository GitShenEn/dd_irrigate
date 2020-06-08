package cn.dreamdeck.service;


import cn.dreamdeck.model.dto.DeviceWaterDto;
import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.model.vo.DeviceStatusVo;
import cn.dreamdeck.model.vo.WaterDateVo;
import cn.dreamdeck.model.vo.WaterVo;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdDeviceWaterService extends IService<DdDeviceWater> {

    /**
     * 分页列表
     *
     * @param page
     * @return
     */
    IPage selectAllPage(Page page, Integer type);

    /**
     * 所有列表
     *
     * @param type (1:未分组，2：已经分组)
     * @return
     */
    List<WaterVo> selectAll(Integer type);

    WaterDateVo getData(Integer deviceId);


    boolean save(DeviceWaterDto waterDto);

    boolean update(DeviceWaterDto waterDto);

    boolean del(Integer deviceId);

    R open(DdDeviceWater ddDeviceWater) ;

    R close(DdDeviceWater ddDeviceWater);

    R status(DdDeviceWater ddDeviceWater);

    R getRealData(DdDeviceWater ddDeviceWater);

    String saveCollectJob(DdDeviceWater deviceWater);

    String delCollectJob(DdDeviceWater deviceWater);

    DeviceStatusVo getByStatus();





}
