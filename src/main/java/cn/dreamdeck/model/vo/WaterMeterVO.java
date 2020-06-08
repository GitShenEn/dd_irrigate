package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用水量")
public class WaterMeterVO {

    @ApiModelProperty(value = "水表类型")
    private Integer type;

    @ApiModelProperty(value = "用水量")
    private Double thisNum;

    @ApiModelProperty(value = "节约水量")
    private Double economize;

    @ApiModelProperty(value = "节省金额")
    private Double money;
}
