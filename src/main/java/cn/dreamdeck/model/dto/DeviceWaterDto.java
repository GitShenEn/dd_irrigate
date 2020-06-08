package cn.dreamdeck.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "新增水表")
public class DeviceWaterDto {

    @ApiModelProperty(value = "水表ID(新增时不传)")
    private Integer deviceId;

    @ApiModelProperty(value = "水表名称")
    private String deviceName;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "网关ID")
    private Integer gatewayId;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "同一网关，同一品牌型号唯一ID")
    private String subsetId;

    @ApiModelProperty(value = "连接端口号")
    private Integer connectPort;

    @ApiModelProperty(value = "喷头个数")
    private Integer nozzleNum;

    @ApiModelProperty(value = "灌溉面积")
    private Float irrigationArea;

}
