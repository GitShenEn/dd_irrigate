package cn.dreamdeck.service.impl;


import cn.dreamdeck.Thread.WateringThread;
import cn.dreamdeck.mapper.DdWarnLogMapper;
import cn.dreamdeck.mapper.DdWarnSetMapper;
import cn.dreamdeck.model.dto.WarnDto;
import cn.dreamdeck.model.entity.DdWarnLog;
import cn.dreamdeck.model.entity.DdWarnSet;
import cn.dreamdeck.model.vo.WarnLogVo;
import cn.dreamdeck.service.DdWarnLogService;
import cn.dreamdeck.utils.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class DdWarnLogServiceImpl extends ServiceImpl<DdWarnLogMapper, DdWarnLog> implements DdWarnLogService {


    private DdWarnSetMapper warnSetMapper;


    @Override
    public Integer getByDeviceStatus(Integer device,Integer type) {
        return this.baseMapper.getByDeviceStatus(device,type);
    }

    @Override
    public IPage getList(Page page, WarnDto warnDto) {
        IPage list = this.baseMapper.getList(page, warnDto);
        List<WarnLogVo> warnLogVoList = list.getRecords();
        for (WarnLogVo vo:warnLogVoList){
            if (vo.getStatus().equals("2")||vo.getStatus().equals("3")){
                vo.setTimeDifference(DateUtil.TimeDifference(vo.getCreateTime(),vo.getDisposeTime()));
            }else{
                vo.setTimeDifference(DateUtil.TimeDifference(vo.getCreateTime(),DateUtil.getTime()));
            }
        }
        list.setRecords(warnLogVoList);
        return list;
    }

    @Override
    public int getNum(String time) {
        return this.baseMapper.getNum(time);
    }

    @Override
    public boolean dispose(Integer logId) {
        DdWarnLog log =  this.baseMapper.selectById(logId);
        if (log!=null){
            log.setStatus("4");
            if (log.getType()==201002){
                DdWarnSet deviceWarn = this.warnSetMapper.getByDeviceId(log.getDeviceId(),201002);
                WateringThread thread = new WateringThread(log.getDeviceId(),deviceWarn.getMaximum(),deviceWarn.getMaximum());
                thread.run();
            }
            this.baseMapper.updateById(log);
            return true;
        }
        return false;
    }
}
