package cn.dreamdeck.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "新增策略传输对象")
public class StrategyDto {

    @ApiModelProperty(value = "主键ID(新增时不传)")
    private Integer strategyId;

    @ApiModelProperty(value = "开始时间（年月日）")
    private String startYtd;

    @ApiModelProperty(value = "结束时间（年月日）")
    private String endYtd;

    @ApiModelProperty(value = "开始时间（时分秒）")
    private String startHms;

    @ApiModelProperty(value = "结束时间（时分秒）")
    private String endHms;

    @ApiModelProperty(value = "所属分区")
    private Integer districtId;

    @ApiModelProperty(value = "开始时长（分钟）")
    private Integer openTime;

    @ApiModelProperty(value = "策略名称")
    private String strategyName;

    @ApiModelProperty(value = "开启频率")
    private Integer openInterval;
}
