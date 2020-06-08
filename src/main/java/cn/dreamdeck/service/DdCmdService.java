package cn.dreamdeck.service;

import cn.dreamdeck.model.entity.DdCmd;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdCmdService extends IService<DdCmd> {

    List<DdCmd> getByDeviceId(Integer deviceId);
}
