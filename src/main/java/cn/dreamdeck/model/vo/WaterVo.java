package cn.dreamdeck.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "水表")
public class WaterVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "水表D")
    private Integer deviceId;

    @ApiModelProperty(value = "组ID")
    private Integer groupId;

    @ApiModelProperty(value = "水表名称")
    private String deviceName;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "开启状态")
    private String isOpen;

    @ApiModelProperty(value = "同一网关，同一品牌型号唯一ID")
    private String subsetId;

    @ApiModelProperty(value = "喷头个数")
    private Integer nozzleNum;

    @ApiModelProperty(value = "灌溉面积")
    private Float irrigationArea;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
