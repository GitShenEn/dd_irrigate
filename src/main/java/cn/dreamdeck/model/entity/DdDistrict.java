package cn.dreamdeck.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "分区表")
@TableName("dd_district")
public class DdDistrict {

    @TableId(value = "district_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Integer districtId;


    @ApiModelProperty(value = "组ID")
    private String districtName;


    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记,1:已删除,0:正常")
    private String delFlag;

}
