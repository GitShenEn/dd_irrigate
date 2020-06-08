package cn.dreamdeck.mapper;


import cn.dreamdeck.model.entity.DdWarnSet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface DdWarnSetMapper extends BaseMapper<DdWarnSet> {

    DdWarnSet getByDeviceId(@Param("deviceId") Integer deviceId, @Param("type") Integer type);

}
