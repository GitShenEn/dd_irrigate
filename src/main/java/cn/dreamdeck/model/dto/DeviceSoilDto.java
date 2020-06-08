package cn.dreamdeck.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "土壤传感器新增")
public class DeviceSoilDto {

    @ApiModelProperty(value = "设备ID(新增时不传)")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "同一网关，同一品牌型号唯一ID")
    private Integer subsetId;

    @ApiModelProperty(value = "连接端口号")
    private Integer connectPort;

    @ApiModelProperty(value = "连接波特率")
    private Integer connectBaud;

    @ApiModelProperty(value = "网关ID")
    private Integer gatewayId;


}
