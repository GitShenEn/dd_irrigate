package cn.dreamdeck.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "墒情返回数据")
public class SoilMoistureVo {

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
