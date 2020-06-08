package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "组关系表")
@TableName("dd_group_water")
public class DdGroupWater {

    @TableId(value = "`id`", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer ID;

    @TableField(value = "group_id")
    @ApiModelProperty(value = "组ID")
    private Integer groupId;

    @TableField(value = "device_id")
    @ApiModelProperty(value = "水表ID")
    private Integer deviceId;
}
