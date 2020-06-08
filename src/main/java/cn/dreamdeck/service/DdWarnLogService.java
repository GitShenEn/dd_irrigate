package cn.dreamdeck.service;


import cn.dreamdeck.model.dto.WarnDto;
import cn.dreamdeck.model.entity.DdWarnLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdWarnLogService extends IService<DdWarnLog> {

    Integer getByDeviceStatus(Integer device, Integer type);

    IPage getList(Page page, WarnDto warnDto);

    int getNum(String time);

    boolean dispose(Integer logId);


}
