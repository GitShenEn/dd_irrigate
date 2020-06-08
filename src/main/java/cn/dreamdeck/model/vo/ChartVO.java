package cn.dreamdeck.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "水图表返回数据")
public class ChartVO {

    @ApiModelProperty(value = "时间")
    private String createTime;

    @ApiModelProperty(value = "用水量")
    private Double num;

    @ApiModelProperty(value = "用水量(ue4要用字符串)")
    private String thisNum ;

}
