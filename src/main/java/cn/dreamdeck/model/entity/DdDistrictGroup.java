package cn.dreamdeck.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分区关系表")
@TableName("dd_district_group")
public class DdDistrictGroup{

    @TableId(value = "`id`", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer ID;

    @ApiModelProperty(value = "区ID")
    private Integer districtId;

    @ApiModelProperty(value = "组ID")
    private Integer groupId;

    @ApiModelProperty(value = "排序")
    private Integer rank;


}
