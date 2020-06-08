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
@ApiModel(value = "告警阈值表")
@TableName("dd_date_warn_set")
public class DdWarnSet {

    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "最大值")
    private Integer maximum;

    @ApiModelProperty(value = "最小值")
    private Integer minimum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;

}
