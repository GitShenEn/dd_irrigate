package cn.dreamdeck.mapper;

import cn.dreamdeck.model.entity.DdCmd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DdCmdMapper extends BaseMapper<DdCmd> {

    List<DdCmd> getByDeviceId(Integer deviceId);

    int delByDeviceId(Integer deviceId);
}
