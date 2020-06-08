package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "组关系")
public class GroupWaterVo {

    @ApiModelProperty(value = "关系ID")
    private Integer ID;

    @ApiModelProperty(value = "组名称")
    private String groupName;

    @ApiModelProperty(value = "水表名称")
    private String deviceName;

    @ApiModelProperty(value = "水表名称")
    private Integer deviceId;
}
