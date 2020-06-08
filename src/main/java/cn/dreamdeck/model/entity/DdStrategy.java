package cn.dreamdeck.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "策略")
@TableName("dd_strategy")
public class DdStrategy {

    @TableId(value = "strategy_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer strategyId;

    @ApiModelProperty(value = "开始时间（年月日）")
    private String startYtd;

    @ApiModelProperty(value = "结束时间（年月日）")
    private String endYtd;

    @ApiModelProperty(value = "开始时间（时分秒）")
    private String startHms;

    @ApiModelProperty(value = "结束时间（时分秒）")
    private String endHms;

    @ApiModelProperty(value = "开关状态")
    private Integer status;

    @ApiModelProperty(value = "所属分区")
    private Integer districtId;

    @ApiModelProperty(value = "开始时长（分钟）")
    private Integer openTime;

    @ApiModelProperty(value = "策略名称")
    private String strategyName;

    @ApiModelProperty(value = "开启频率")
    private Integer openInterval;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;
}
