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
@ApiModel(value = "水表开关记录")
@TableName("dd_data_switch")
public class DdDataSwitch implements Serializable {


    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @ApiModelProperty(value = "开关类型")
    private String switchType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作人")
    private String createPersonnel;

}
