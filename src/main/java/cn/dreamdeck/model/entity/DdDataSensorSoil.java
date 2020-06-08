package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "土壤传感器表")
@TableName("dd_data_sensor_soil")
public class DdDataSensorSoil implements Serializable {

    public DdDataSensorSoil(){
        this.setHumidity(0f);
        this.setTemperature(0f);
        this.setSalinity(0f);
    }


    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @ApiModelProperty(value = "温度")
    private Float temperature;

    @ApiModelProperty(value = "湿度")
    private Float humidity;

    @ApiModelProperty(value = "盐分")
    private Float salinity;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
