package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "土壤设备表")
@TableName("dd_device_soil")
public class DdDeviceSoil implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id", type = IdType.AUTO)
    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;


    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "关联水表")
    private Integer waterId;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "同一网关，同一品牌型号唯一ID")
    private Integer subsetId;

    @ApiModelProperty(value = "连接端口号")
    private Integer connectPort;

    @ApiModelProperty(value = "连接波特率")
    private Integer connectBaud;

    @ApiModelProperty(value = "网关ID")
    private Integer gatewayId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;
}
