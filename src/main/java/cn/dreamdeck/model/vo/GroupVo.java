package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分组返回管理")
public class GroupVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组ID")
    private Integer groupId;

    @ApiModelProperty(value = "分组名称")
    private String groupName;
}
