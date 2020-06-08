package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "策略")
public class StrategyVo {
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

    @ApiModelProperty(value = "策略名称")
    private String strategyName;

    @ApiModelProperty(value = "开启频率(默认一天一次)")
    private Integer openInterval;

    @ApiModelProperty(value = "开始时长（默认三十分钟）")
    private Integer openTime;
}
