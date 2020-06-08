package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分区返回管理")
public class DistrictGroupVo {

    @ApiModelProperty(value = "关系ID")
    private Integer ID;

    @ApiModelProperty(value = "区名称")
    private String districtName;

    @ApiModelProperty(value = "组名称")
    private String groupName;

    @ApiModelProperty(value = "组ID")
    private Integer groupId;

    @ApiModelProperty(value = "排序")
    private Integer rank;


}
