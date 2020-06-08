package cn.dreamdeck.mapper;


import cn.dreamdeck.model.dto.WarnDto;
import cn.dreamdeck.model.entity.DdWarnLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface DdWarnLogMapper extends BaseMapper<DdWarnLog> {

    Integer getByDeviceStatus(@Param("deviceId") Integer deviceId, @Param("type") Integer type);

    IPage getList(Page page, @Param("warnDto") WarnDto warnDto);

    int getNum(@Param("time") String time);

}
