package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.*;
import cn.dreamdeck.model.CommonConstants;
import cn.dreamdeck.model.dto.DeviceWaterDto;
import cn.dreamdeck.model.entity.*;
import cn.dreamdeck.model.irrigationConstants;
import cn.dreamdeck.model.vo.*;
import cn.dreamdeck.service.DdDeviceSoilService;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.utils.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class DdDeviceWaterServiceImpl extends ServiceImpl<DdDeviceWaterMapper, DdDeviceWater> implements DdDeviceWaterService {

    private DdDataWaterMeterMapper ddDataWaterMeterMapper;

    private DdGroupWaterMapper ddGroupWaterMapper;

    private RedisUtil redisUtil;



    private DdDeviceSoilMapper ddDeviceSoilMapper;

    private DdDeviceGatewayMapper ddDeviceGatewayMapper;

    private DdDeviceSoilService deviceSoilService;

    private DdDataSwitchMapper ddDataSwitchMapper;

    private DdDataSoilMoistureMapper dataSoilMoistureMapper;


    @Override
    public IPage selectAllPage(Page page, Integer type) {
        return this.baseMapper.selectAll(page, type);
    }

    @Override
    public List<WaterVo> selectAll(Integer type) {
        return this.baseMapper.selectAll(type);
    }

    @Override
    public WaterDateVo getData(Integer deviceId) {
        WaterDateVo vo = new WaterDateVo();
        DdDeviceWater water = this.baseMapper.selectById(deviceId);
        if (water != null) {
            BeanUtils.copyProperties(water, vo);
            vo.setStatus(water.getStatus());
            vo.setIsOPen(water.getIsOpen());
            vo.setTotalWatering(this.baseMapper.selectSwitch(deviceId, null));
            vo.setLastWatering(this.baseMapper.selectSwitch(deviceId, DateUtil.getLastMonth()));
            vo.setTotalWaterNum(ddDataWaterMeterMapper.selectNum(deviceId, null));
            vo.setLastWaterNum(ddDataWaterMeterMapper.selectNum(deviceId, DateUtil.getLastMonth()));
            vo.setLatitude(water.getLatitude());
            vo.setLongitude(water.getLongitude());
            DdDataSensorSoil sensorSoil = deviceSoilService.getDataByWaterId(deviceId);
            if (sensorSoil != null) {
                vo.setHumidity(sensorSoil.getHumidity());
            } else {
                vo.setHumidity(0f);
            }
        }
        return vo;
    }


    public boolean hasKey(Integer strategyId) {
        return redisUtil.hasKey(irrigationConstants.STATE + strategyId);
    }


    public List<FailedVo> groupClose(Integer groupId, List<FailedVo> closeFailed) {
        List<GroupWaterVo> deviceList = ddGroupWaterMapper.selectByGroup(groupId);
        try {
            for (GroupWaterVo water : deviceList) {
                DdDeviceWater ddDeviceWater = this.baseMapper.selectById(water.getDeviceId());
                int code = this.close(ddDeviceWater).getCode();
                if (close(ddDeviceWater).getCode() == 1) {
                    R returnS = close(ddDeviceWater);
                    if (returnS.getCode() == 1) {
                        FailedVo vo = new FailedVo();
                        vo.setDeviceId(water.getDeviceId());
                        vo.setMsg(returnS.getMsg());
                        closeFailed.add(vo);
                        redisUtil.set(irrigationConstants.FAILED, closeFailed);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return closeFailed;
    }


    public List<FailedVo> groupOpen(Integer groupId, List<FailedVo> openFailed) {
        List<GroupWaterVo> deviceList = ddGroupWaterMapper.selectByGroup(groupId);
        try {
            for (GroupWaterVo water : deviceList) {
                DdDeviceWater ddDeviceWater = this.baseMapper.selectById(water.getDeviceId());
                if (open(ddDeviceWater).getCode() == 1) {
                    R returnS = open(ddDeviceWater);
                    if (returnS.getCode() == 1) {
                        FailedVo vo = new FailedVo();
                        vo.setDeviceId(water.getDeviceId());
                        vo.setMsg(returnS.getMsg());
                        openFailed.add(vo);
                        redisUtil.set(irrigationConstants.FAILED, openFailed);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return openFailed;
    }


    public void setJobMsg(JobVo vo) {
        redisUtil.set(irrigationConstants.STRATEGY + vo.getStrategyId(), vo);
    }

    public void closeJob(Integer strategyId) {
        redisUtil.del(irrigationConstants.STRATEGY + strategyId);
        redisUtil.del(irrigationConstants.STATE + strategyId);
    }


    @Override
    public boolean save(DeviceWaterDto waterDto) {
        try {
            DdDeviceWater ddDeviceWater = new DdDeviceWater();
            BeanUtils.copyProperties(waterDto, ddDeviceWater);
            ddDeviceWater.setDelFlag(CommonConstants.STATUS_NORMAL);
            ddDeviceWater.setIsOpen(CommonConstants.IS_OPEN_NO);
            ddDeviceWater.setStatus(CommonConstants.STATUS_OFF);
            ddDeviceWater.setCmdOpen(generateCmdOpen(waterDto.getSubsetId()));
            ddDeviceWater.setCmdClose(generateCmdClose(waterDto.getSubsetId()));
            ddDeviceWater.setCmdData(generateCmdData(waterDto.getSubsetId()));
            ddDeviceWater.setCmdStatus(generateCmdStatus(waterDto.getSubsetId()));
            this.baseMapper.insert(ddDeviceWater);
            saveCollectJob(ddDeviceWater);
        } catch (BeansException e) {
            return false;
        }
        return true;

    }

    @Override
    public boolean update(DeviceWaterDto waterDto) {
        DdDeviceWater ddDeviceWater = this.baseMapper.selectById(waterDto.getDeviceId());
        if (ddDeviceWater != null) {
            BeanUtils.copyProperties(waterDto, ddDeviceWater);
            if (waterDto.getSubsetId() != null) {
                ddDeviceWater.setSubsetId(waterDto.getSubsetId());
                ddDeviceWater.setCmdOpen(generateCmdOpen(waterDto.getSubsetId()));
                ddDeviceWater.setCmdClose(generateCmdClose(waterDto.getSubsetId()));
                ddDeviceWater.setCmdData(generateCmdData(waterDto.getSubsetId()));
                ddDeviceWater.setCmdStatus(generateCmdStatus(waterDto.getSubsetId()));
            }
            if (waterDto.getDeviceName()!=null){
                saveCollectJob(ddDeviceWater);
            }
            this.baseMapper.updateById(ddDeviceWater);
            return true;
        }
        return false;
    }

    @Override
    public boolean del(Integer deviceId) {
        DdDeviceWater ddDeviceWater = this.baseMapper.selectById(deviceId);
        if (ddDeviceWater != null) {
            ddDeviceWater.setDelFlag(CommonConstants.STATUS_DEL);
            this.baseMapper.updateById(ddDeviceWater);
            //删除采集任务
            delCollectJob(ddDeviceWater);
            //删除组关系
            ddGroupWaterMapper.delete((Wrappers.<DdGroupWater>lambdaQuery().eq(DdGroupWater::getDeviceId, deviceId)));
            //删除土壤设备关联
            List<DdDeviceSoil> list = ddDeviceSoilMapper.selectList(Wrappers.<DdDeviceSoil>lambdaQuery().eq(DdDeviceSoil::getWaterId, deviceId));
            if (list != null && list.size() > 0) {
                for (DdDeviceSoil soil : list) {
                    soil.setWaterId(null);
                    ddDeviceSoilMapper.updateById(soil);
                    return true;
                }
            }

        }
        return false;
    }


    @Override
    public R open(DdDeviceWater ddDeviceWater) {
        try {
            R resultR = getResult(ddDeviceWater.getGatewayId(), ddDeviceWater.getCmdOpen(), ddDeviceWater.getConnectPort(), 8);
            if (resultR.getCode() == 0) {
                String result = (String) resultR.getData();
                if (ddDeviceWater.getSubsetId().equals(result.substring(0, 2))) {
                    ddDeviceWater.setStatus(CommonConstants.STATUS_ON);
                    ddDeviceWater.setIsOpen(CommonConstants.IS_OPEN_YES);
                    this.baseMapper.updateById(ddDeviceWater);
                    this.soilSwitchLog(ddDeviceWater);//type=1 开
                    return R.ok("开阀成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.failed("开阀失败");
    }

    @Override
    public R close(DdDeviceWater ddDeviceWater) {
        try {
            R resultR = getResult(ddDeviceWater.getGatewayId(), ddDeviceWater.getCmdClose(), ddDeviceWater.getConnectPort(), 8);
            if (resultR.getCode() == 0) {
                String result = (String) resultR.getData();
                if (ddDeviceWater.getSubsetId().equals(result.substring(0, 2))) {
                    ddDeviceWater.setStatus(CommonConstants.STATUS_ON);
                    ddDeviceWater.setIsOpen(CommonConstants.IS_OPEN_NO);
                    this.baseMapper.updateById(ddDeviceWater);
                    this.soilSwitchLog(ddDeviceWater); // type=2 关
                    return R.ok("关阀成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.failed("关阀失败");
    }


    @Override
    public R status(DdDeviceWater ddDeviceWater) {
        try {
            R resultR = getResult(ddDeviceWater.getGatewayId(), ddDeviceWater.getCmdStatus(), ddDeviceWater.getConnectPort(), 7);
            if (resultR.getCode() == 0) {
                String result = (String) resultR.getData();
                if (result.substring(0, 2).equals(ddDeviceWater.getSubsetId().toString())) {
                    String status = Crc16Util.convert16To2(result.substring(6, 10));
                    status = status.substring(status.length() - 2, status.length());
                    if (status.equals("01")) {
                        //开阀
                        ddDeviceWater.setStatus(CommonConstants.STATUS_ON);
                        ddDeviceWater.setIsOpen(CommonConstants.IS_OPEN_YES);
                        this.baseMapper.updateById(ddDeviceWater);
                        return R.ok("1");
                    } else if (status.equals("10")) {
                        //关阀
                        ddDeviceWater.setStatus(CommonConstants.STATUS_ON);
                        ddDeviceWater.setIsOpen(CommonConstants.IS_OPEN_NO);
                        this.baseMapper.updateById(ddDeviceWater);
                        return R.ok("0");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.failed("查询水表状态失败");
    }


    @Override
    public R getRealData(DdDeviceWater ddDeviceWater) {
        try {
            R resultR = getResult(ddDeviceWater.getGatewayId(), ddDeviceWater.getCmdData(), ddDeviceWater.getConnectPort(), 7);
            if (resultR.getCode() == 0) {
                String result = (String) resultR.getData();
                if (result.substring(0, 2).equals(ddDeviceWater.getSubsetId().toString())) {
                    int value = Integer.parseInt(result.substring(6, 10), 16);
                    Float rValue = value / 1000f;
                    return R.ok(rValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.failed("查询水表数据失败");
    }


    /**
     * 生成水表开启命令
     *
     * @param subsetId
     * @return
     */
    private String generateCmdOpen(String subsetId) {
        /**
         * 宁波诺曼特仪表公司
         * 智能水表
         * 集中器发送：subsetId 10 00 60 00 01 02 63 01 AD 30  自由 开阀  99开度
         */
        byte[] code = new byte[9];
        code[0] = (byte) Integer.parseInt(String.valueOf(subsetId), 16);
        code[1] = (byte) 0x10;
        code[2] = (byte) 0x00;
        code[3] = (byte) 0x60;
        code[4] = (byte) 0x00;
        code[5] = (byte) 0x01;
        code[6] = (byte) 0x02;
        code[7] = (byte) 0x63;
        code[8] = (byte) 0x01;
        byte[] result = Crc16Util.getData(code);
        String cmdData = Crc16Util.byteTo16String(result);
        return cmdData;
    }

    /**
     * 生成水表关闭命令
     *
     * @param subsetId
     * @return
     */
    private String generateCmdClose(String subsetId) {
        /**
         * 宁波诺曼特仪表公司
         * 智能水表
         */
        byte[] code = new byte[9];
        code[0] = (byte) Integer.parseInt(String.valueOf(subsetId), 16);
        code[1] = (byte) 0x10;
        code[2] = (byte) 0x00;
        code[3] = (byte) 0x60;
        code[4] = (byte) 0x00;
        code[5] = (byte) 0x01;
        code[6] = (byte) 0x02;
        code[7] = (byte) 0x63;
        code[8] = (byte) 0x02;
        byte[] result = Crc16Util.getData(code);
        String cmdData = Crc16Util.byteTo16String(result);
        return cmdData;
    }

    /**
     * 生成水表采集命令
     *
     * @param subsetId
     * @return
     */
    private String generateCmdData(String subsetId) {
        /**
         * 宁波诺曼特仪表公司
         * 智能水表
         */
        byte[] code = new byte[6];
        code[0] = (byte) Integer.parseInt(String.valueOf(subsetId), 16);
        code[1] = (byte) 0x03;
        code[2] = (byte) 0x00;
        code[3] = (byte) 0x0E;
        code[4] = (byte) 0x00;
        code[5] = (byte) 0x01;
        byte[] result = Crc16Util.getData(code);
        String cmdData = Crc16Util.byteTo16String(result);
        return cmdData;
    }

    /**
     * 生成水表查询命令
     *
     * @param subsetId
     * @return
     */
    private String generateCmdStatus(String subsetId) {
        /**
         * 宁波诺曼特仪表公司
         * 智能水表
         */
        byte[] code = new byte[6];
        code[0] = (byte) Integer.parseInt(String.valueOf(subsetId), 16);
        code[1] = (byte) 0x03;
        code[2] = (byte) 0x00;
        code[3] = (byte) 0x60;
        code[4] = (byte) 0x00;
        code[5] = (byte) 0x01;
        byte[] result = Crc16Util.getData(code);
        String cmdData = Crc16Util.byteTo16String(result);
        return cmdData;
    }

    public String delCollectJob(DdDeviceWater deviceWater) {
//        if (deviceWater != null) {
//            //先删除
//            remoteJobService.shutdownJob("collect_water_" + deviceWater.getDeviceId());
//            remoteJobService.removeById("collect_water_" + deviceWater.getDeviceId());
//            return "成功";
//        }
        return "失败";
    }




    public String saveCollectJob(DdDeviceWater deviceWater) {
//        if (deviceWater != null) {
//            delCollectJob(deviceWater);
//            SysJob startJob = new SysJob();
//            startJob.setJobId("collect_water_" + deviceWater.getDeviceId());
//            startJob.setJobName(deviceWater.getDeviceName() + "定时采集任务");
//            startJob.setJobGroup("collect_water_" + deviceWater.getDeviceId());
//            startJob.setJobOrder("9");
//            startJob.setJobType("3");
//            startJob.setExecutePath("http://dd-gateway:9999/irrigation/water/collect/" + deviceWater.getDeviceId());
//            startJob.setCronExpression("0 0/5 * * * ?");
//            startJob.setMisfirePolicy("1");
//            startJob.setJobTenantType("2");
//            startJob.setCreateBy("collectWater");
//            remoteJobService.save(startJob, SecurityConstants.FROM);
//            remoteJobService.startJob(startJob.getJobId());
//            return "成功";
//        }
        return "失败";
    }

    /**
     * 土地墒情记录
     *
     * @param ddDeviceWater 设备ID
     */
    public void soilSwitchLog(DdDeviceWater ddDeviceWater) {
        try {
            Integer deviceId = ddDeviceWater.getDeviceId();
            String type = ddDeviceWater.getIsOpen();
            String key = irrigationConstants.SOIL_MOISTURE + deviceId+type;
            if (!redisUtil.hasKey(key)) {
                //添加水表开关记录
                DdDataSwitch dataSwitch = new DdDataSwitch();
                dataSwitch.setDeviceId(deviceId);
                dataSwitch.setSwitchType(type);
                ddDataSwitchMapper.insert(dataSwitch);
                //查询水表土壤对应关系
                DdDataSensorSoil sensorSoil = deviceSoilService.getDataByWaterId(deviceId);
                if (sensorSoil != null) {

                    //最新的土壤湿度
                    Float humidity = sensorSoil.getHumidity();
                    //最新的水表用水量
                    Float waterNum = 0f;
                    if (redisUtil.hasKey(irrigationConstants.DATA_WATER + deviceId)) {
                        DdDataWaterMeter ddDataWaterMeter = (DdDataWaterMeter) redisUtil.get(irrigationConstants.DATA_WATER + deviceId);
                        waterNum = ddDataWaterMeter.getWaterNum();
                    } else {
                        R r = this.getRealData(ddDeviceWater);
                        if (r.getCode() == 0) {
                            waterNum = (Float) r.getData();
                        } else {
                            return;
                        }
                    }
                    if (type.equals(CommonConstants.IS_OPEN_YES)) {//水表开启时
                        WaterOPenVo oPenVo = new WaterOPenVo();
                        oPenVo.setDeviceId(deviceId);
                        oPenVo.setHumidity(humidity);
                        oPenVo.setWaterNum(waterNum);
                        oPenVo.setCreateTime(DateUtil.getTime());
                        redisUtil.set(key, oPenVo);
                    } else if (type.equals(CommonConstants.IS_OPEN_NO)) {
                        //从缓存中获取此设备开启时的数据
                        if (redisUtil.hasKey(irrigationConstants.SOIL_MOISTURE + deviceId+CommonConstants.IS_OPEN_YES)) {
                            WaterOPenVo oPenVo = (WaterOPenVo) redisUtil.get(irrigationConstants.SOIL_MOISTURE + deviceId+CommonConstants.IS_OPEN_YES);
                            Float waterValue = waterNum - oPenVo.getWaterNum();//灌溉用水
                            Float humidityValue = humidity - oPenVo.getHumidity();//上升比例
                            String humiditySQ = oPenVo.getHumidity().toString() + "--" + humidity.toString();//起末土壤墒情
                            String timeSub = DateUtil.getTimeSub(oPenVo.getCreateTime(), DateUtil.getTime());//灌溉时间
                            //将计算好的数据插入土壤墒情表
                            DdDataSoilMoisture ddDataSoilmoistureLog = new DdDataSoilMoisture();
                            ddDataSoilmoistureLog.setWateringNum(waterValue);
                            ddDataSoilmoistureLog.setRiseRatio(humidityValue);
                            ddDataSoilmoistureLog.setSoilMoisture(humiditySQ);
                            ddDataSoilmoistureLog.setWateringTime(timeSub);
                            ddDataSoilmoistureLog.setWaterId(deviceId);
                            dataSoilMoistureMapper.insert(ddDataSoilmoistureLog);
                            redisUtil.del(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public R getResult(Integer gatewayId, String cmd, Integer connectPort, Integer byteSize) {
        try {
            DdDeviceGateway ddDeviceGateway = ddDeviceGatewayMapper.selectById(gatewayId);
            if (ddDeviceGateway == null) {
                return R.failed("网关数据不存在");
            }

            if (!ddDeviceGateway.getStatus().equals(CommonConstants.STATUS_ON)) {
                return R.failed("网关已离线");
            }
            byte[] bytes = Util.hexStrToBinaryStr(cmd);
            Socket socket = new Socket(ddDeviceGateway.getIp(), connectPort);
            //设置超时时间
            socket.setSoTimeout(1000);
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            os.write(bytes);
            os.flush();
            Thread.sleep(100);
            byte[] prefixByte = new byte[byteSize];
            socket.getInputStream().read(prefixByte);
            String result = Util.byte2hex(prefixByte);
            if (result != null) {
                return R.ok(result);
            } else {
                return R.failed("连接失败");
            }
        } catch (Exception e) {
            return R.failed("连接失败");
        }
    }

    @Override
    public DeviceStatusVo getByStatus() {
        DeviceStatusVo vo = new DeviceStatusVo();
        Integer onSoil = ddDeviceSoilMapper.selectList(Wrappers.<DdDeviceSoil>lambdaQuery().eq(DdDeviceSoil::getDelFlag,"0").eq(DdDeviceSoil::getStatus,"1")).size();
        Integer offSoil = ddDeviceSoilMapper.selectList(Wrappers.<DdDeviceSoil>lambdaQuery().eq(DdDeviceSoil::getDelFlag,"0").eq(DdDeviceSoil::getStatus,"0")).size();
        Integer onWater = this.baseMapper.selectList(Wrappers.<DdDeviceWater>lambdaQuery().eq(DdDeviceWater::getDelFlag,"0").eq(DdDeviceWater::getStatus,"1")).size();
        Integer offWater = this.baseMapper.selectList(Wrappers.<DdDeviceWater>lambdaQuery().eq(DdDeviceWater::getDelFlag,"0").eq(DdDeviceWater::getStatus,"0")).size();
        vo.setOnline(onSoil+onWater);
        vo.setOffline(offSoil+offWater);
        vo.setSum(vo.getOffline()+vo.getOnline());
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        vo.setOnlineRate(numberFormat.format((float) vo.getOnline() / (float) vo.getSum() * 100));
        vo.setOfflineRate(numberFormat.format((float) vo.getOffline() / (float) vo.getSum() * 100));
        return vo;
    }


}
