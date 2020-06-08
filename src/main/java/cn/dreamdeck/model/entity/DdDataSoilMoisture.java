package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "土壤墒情数据")
@TableName("dd_data_soilmoisture")
public class DdDataSoilMoisture {

    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "水表ID")
    private Integer waterId;

    @ApiModelProperty(value = "上升比例")
    private Float riseRatio;

    @ApiModelProperty(value = "用水量")
    private Float wateringNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "灌溉时长")
    private String wateringTime;

    @ApiModelProperty(value = "起末土壤墒情")
    private String soilMoisture;
}
