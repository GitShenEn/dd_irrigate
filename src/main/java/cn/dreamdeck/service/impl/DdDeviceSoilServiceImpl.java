package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.DdCmdMapper;
import cn.dreamdeck.mapper.DdDeviceSoilMapper;
import cn.dreamdeck.model.dto.DeviceSoilDto;
import cn.dreamdeck.model.entity.DdCmd;
import cn.dreamdeck.model.entity.DdDataSensorSoil;
import cn.dreamdeck.model.entity.DdDeviceSoil;
import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.model.vo.DdDeviceSoilVo;
import cn.dreamdeck.model.vo.SoilDataVo;
import cn.dreamdeck.service.DdDeviceSoilService;
import cn.dreamdeck.utils.Crc16Util;
import cn.dreamdeck.utils.RedisUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor

public class DdDeviceSoilServiceImpl extends ServiceImpl<DdDeviceSoilMapper, DdDeviceSoil> implements DdDeviceSoilService {

    private DdCmdMapper cmdMapper;

    private DdDeviceSoilMapper soilMapper;


    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DeviceSoilDto soilDto) {
        DdDeviceSoil deviceSoil = new DdDeviceSoil();
        BeanUtils.copyProperties(soilDto, deviceSoil);
        deviceSoil.setDelFlag("0");
        deviceSoil.setStatus("0");
        soilMapper.insert(deviceSoil);
        //生成采集命令
        generateCmdData(deviceSoil.getDeviceId(),deviceSoil.getSubsetId());
        //生成采集定时任务
        saveCollectJob(deviceSoil);
        return true;
    }

    @Override
    public boolean del(Integer deviceId) {
       DdDeviceSoil deviceSoil =   soilMapper.selectById(deviceId);
       if (deviceSoil!=null){
           deviceSoil.setDelFlag("1");
           soilMapper.updateById(deviceSoil);
           cmdMapper.delByDeviceId(deviceId);
           delCollectJob(deviceSoil);
           return true;
       }
       return false;
    }


    @Override
    public SoilDataVo getDataById(Integer deviceId) {
        SoilDataVo vo = new SoilDataVo();
        DdDeviceSoil deviceSoil =   this.baseMapper.selectById(deviceId);
        if (deviceSoil!=null){
            BeanUtils.copyProperties(deviceSoil, vo);
        }
        if (redisUtil.hasKey((irrigationConstants.DATA_SOIL+deviceId))){
            DdDataSensorSoil sensorSoil = (DdDataSensorSoil)redisUtil.get((irrigationConstants.DATA_SOIL+deviceId));
            BeanUtils.copyProperties(sensorSoil, vo);
        }
        return vo;
    }

    @Override
    public DdDataSensorSoil getDataByWaterId(Integer deviceId) {
        DdDataSensorSoil vo = new DdDataSensorSoil();
        List<DdDeviceSoil> list =  soilMapper.selectList(Wrappers.<DdDeviceSoil>lambdaQuery().eq(DdDeviceSoil::getWaterId,deviceId));
        Float humidity = 0f;
        Float salinity = 0f;
        Float temperature = 0f;
        if (list!=null&&list.size()>0){
            int i = 0;
            for (DdDeviceSoil soil:list){
                if (redisUtil.hasKey(irrigationConstants.DATA_SOIL+soil.getDeviceId())){
                    DdDataSensorSoil   soilVo = (DdDataSensorSoil)redisUtil.get(irrigationConstants.DATA_SOIL+soil.getDeviceId());
                    humidity +=soilVo.getHumidity();
                    salinity +=soilVo.getSalinity();
                    temperature+=soilVo.getTemperature();
                    i++;
                }
            }
            if (i==0){
                return null;
            }
            vo.setHumidity(humidity/i);
            vo.setSalinity(salinity/i);
            vo.setTemperature(temperature/i);
            return vo;
        }else{
            return null;
        }

    }

    @Override
    public List<DdDeviceSoilVo> getList() {
        return this.baseMapper.getList();
    }


    /**
     * 生成土壤传感器采集命令
     */
    private String generateCmdData(Integer deviceId, int subsetId) {
        /**
         * 土壤传感器一
         * 山东威海精迅畅通电子科技
         * JXBS-3001-TR-RS-4
         * 10 14
         */
        //10 14
        cmdMapper.delByDeviceId(deviceId);
        byte[] code = new byte[6];
        code[0] = (byte) subsetId;
        code[1] = (byte) 0x03;
        code[2] = (byte) 0x00;
        code[3] = (byte) 0x12;
        code[4] = (byte) 0x00;
        code[5] = (byte) 0x04;
        byte[] result = Crc16Util.getData(code);
        String cmdData = Crc16Util.byteTo16String(result);
        DdCmd temperature = new DdCmd();
        temperature.setDeviceId(deviceId);
        temperature.setCmdType(1);
        temperature.setName("温度");
        temperature.setCmdData(cmdData);
        temperature.setDecimal(1);
        temperature.setStart(10);
        temperature.setEnd(14);
        temperature.setStatus("1");
        cmdMapper.insert(temperature);
        //6 10
        byte[] humidity = new byte[6];
        humidity[0] = (byte) subsetId;
        humidity[1] = (byte) 0x03;
        humidity[2] = (byte) 0x00;
        humidity[3] = (byte) 0x12;
        humidity[4] = (byte) 0x00;
        humidity[5] = (byte) 0x04;
        byte[] humidityResult = Crc16Util.getData(humidity);
        String humidityCmdData = Crc16Util.byteTo16String(humidityResult);
        DdCmd humidityCmd = new DdCmd();
        humidityCmd.setDeviceId(deviceId);
        humidityCmd.setCmdType(2);
        humidityCmd.setName("湿度");
        humidityCmd.setCmdData(humidityCmdData);
        humidityCmd.setDecimal(1);
        humidityCmd.setStart(6);
        humidityCmd.setEnd(10);
        humidityCmd.setStatus("1");
        cmdMapper.insert(humidityCmd);

        //14 18
        byte[] salinity = new byte[6];
        salinity[0]=(byte)subsetId;
        salinity[1]=(byte)0x03;
        salinity[2]=(byte)0x00;
        salinity[3]=(byte)0x12;
        salinity[4]=(byte)0x00;
        salinity[5]=(byte)0x04;
        byte[] salinityResult = Crc16Util.getData(salinity);
        String salinityCmdData = Crc16Util.byteTo16String(salinityResult);
        DdCmd sensorCmd = new DdCmd();
        sensorCmd.setDeviceId(deviceId);
        sensorCmd.setCmdType(3);
        sensorCmd.setName("盐分");
        sensorCmd.setCmdData(salinityCmdData);
        sensorCmd.setDecimal(2);
        sensorCmd.setStart(14);
        sensorCmd.setEnd(18);
        sensorCmd.setStatus("1");
        cmdMapper.insert(sensorCmd);
        return "ok";
    }



    public String  delCollectJob(DdDeviceSoil deviceSoil){
//        if (deviceSoil!=null) {
//            //先删除
//            remoteJobService.shutdownJob("collect_soil_" + deviceSoil.getDeviceId());
//            remoteJobService.removeById("collect_soil_" + deviceSoil.getDeviceId());
//            return "删除成功";
//        }
        return "失败";
    }




    public String saveCollectJob(DdDeviceSoil deviceSoil) {
//        if (deviceSoil!=null){
//            delCollectJob(deviceSoil);
//            SysJob startJob = new SysJob();
//            startJob.setJobId("collect_soil_" +deviceSoil.getDeviceId());
//            startJob.setJobName(deviceSoil.getName()+"定时采集任务");
//            startJob.setJobGroup("collect_soil_" + deviceSoil.getDeviceId());
//            startJob.setJobOrder("9");
//            startJob.setJobType("3");
//            startJob.setExecutePath("http://dd-gateway:9999/irrigation/soil/collect/" + deviceSoil.getDeviceId());
//            startJob.setCronExpression("0 0/5 * * * ?");
//            startJob.setMisfirePolicy("1");
//            startJob.setJobTenantType("2");
//            startJob.setCreateBy("collectSoil");
//            remoteJobService.save(startJob, SecurityConstants.FROM);
//            remoteJobService.startJob(startJob.getJobId());
//            return "开启成功";
//        }
        return "失败";
    }





}





