package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "告警返回数据")
public class WarnLogVo {

    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "分类")
    private Integer classify;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "告警值")
    private Double value;

    @ApiModelProperty(value = "处理人")
    private String disposePeople;

    @ApiModelProperty(value = "持续时间")
    private String TimeDifference;

    @ApiModelProperty(value = "处理时间")
    private String disposeTime;

    @ApiModelProperty(value = "响应时间")
    private String responseTime;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "告警原因")
    private String dictName;

    @ApiModelProperty(value = "处理意见")
    private String remark;
}
