package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "水表详情返回值")
public class WaterDateVo {

    @ApiModelProperty(value = "水表D")
    private Integer deviceId;

    @ApiModelProperty(value = "水表名称")
    private String deviceName;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "开关状态")
    private String isOPen;

    @ApiModelProperty(value = "喷头个数")
    private Integer nozzleNum;

    @ApiModelProperty(value = "灌溉面积")
    private Float irrigationArea;

    @ApiModelProperty(value = "总用水")
    private Float totalWaterNum;

    @ApiModelProperty(value = "上月用水")
    private Float lastWaterNum;

    @ApiModelProperty(value = "灌溉次数")
    private Integer totalWatering;

    @ApiModelProperty(value = "上月灌溉次数")
    private Integer lastWatering;

    @ApiModelProperty(value = "土壤湿度")
    private Float humidity;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;




}
