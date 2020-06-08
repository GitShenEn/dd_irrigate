package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "Job信息")
public class JobVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "策略ID")
    private Integer StrategyId;

    @ApiModelProperty(value = "策略名称")
    private String jobName;

    @ApiModelProperty(value = "结束时间")
    private String EndHms;

    @ApiModelProperty(value = "组剩余时间")
    private Integer thisNum;

    @ApiModelProperty(value = "组ID")
    private Integer groupId;
}
