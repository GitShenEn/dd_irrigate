package cn.dreamdeck.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分区返回管理")
public class DistrictVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组ID")
    private Integer DistrictId;

    @ApiModelProperty(value = "分组名称")
    private String DistrictName;


}
