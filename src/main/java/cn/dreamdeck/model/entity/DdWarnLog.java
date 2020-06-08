package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "告警数据表")
@TableName("dd_date_warn_log")
public class DdWarnLog {

    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型")
    private Integer type;

    @TableField(value = "`classify`")
    @ApiModelProperty(value = "类型")
    private Integer classify;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;

    @TableField(value = "`value`")
    @ApiModelProperty(value = "告警值")
    private Float value;

    @ApiModelProperty(value = "处理人")
    private String disposePeople;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime disposeTime;

    @ApiModelProperty(value = "响应时间")
    private LocalDateTime responseTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
