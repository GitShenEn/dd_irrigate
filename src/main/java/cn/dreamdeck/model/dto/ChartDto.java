package cn.dreamdeck.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "单个图表传输对象")
public class ChartDto {


    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "时间类型（1:年 2:年月 3:年月日）")
    private Integer timeType;

    @ApiModelProperty(value = "设备ID（非必传）")
    private Integer deviceId;

    @ApiModelProperty(value = "设备类型（1:普通区水表 2:智能区水表）")
    private Integer deviceType;
}
