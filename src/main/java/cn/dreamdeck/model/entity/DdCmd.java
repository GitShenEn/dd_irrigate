package cn.dreamdeck.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设备命令表表")
@TableName("dd_cmd")
public class DdCmd {

    @TableId(value = "cmd_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer cmdId;

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @ApiModelProperty(value = "命令类型")
    private Integer cmdType;

    @TableField(value = "`name`")
    @ApiModelProperty(value = "命令名称")
    private String name;

    @ApiModelProperty(value = "命令")
    private String cmdData;

    @TableField(value = "`decimal`")
    @ApiModelProperty(value = "小数点")
    private Integer decimal;

    @TableField(value = "`start`")
    @ApiModelProperty(value = "开始")
    private Integer start;

    @TableField(value = "`end`")
    @ApiModelProperty(value = "结束")
    private Integer end;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private String status;
}
