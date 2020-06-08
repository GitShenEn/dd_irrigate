package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "水表设备表")
@TableName("dd_device_water")
public class DdDeviceWater  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id", type = IdType.AUTO)
    @ApiModelProperty(value = "水表ID")
    private Integer deviceId;

    @TableField(value = "device_name")
    @ApiModelProperty(value = "水表名称")
    private String deviceName;

    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型")
    private String type;

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

    @ApiModelProperty(value = "连接端口号")
    private Integer connectPort;

    @ApiModelProperty(value = "开阀命令")
    private String cmdOpen;

    @ApiModelProperty(value = "关阀命令")
    private String cmdClose;

    @ApiModelProperty(value = "数据采集命令")
    private String cmdData;

    @ApiModelProperty(value = "状态查询命令")
    private String cmdStatus;

    @ApiModelProperty(value = "网关ID")
    private Integer gatewayId;

    @ApiModelProperty(value = "喷头个数")
    private Integer nozzleNum;

    @ApiModelProperty(value = "灌溉面积")
    private Float irrigationArea;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;
}
