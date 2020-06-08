package cn.dreamdeck.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "告警查询传输对象")
public class WarnDto {


    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "告警分类")
    private Integer classify;

    @ApiModelProperty(value = "告警类型")
    private Integer type;

    @ApiModelProperty(value = "告警状态(3:人工处理,2:自动处理,1:未处理)")
    private String status;

}
