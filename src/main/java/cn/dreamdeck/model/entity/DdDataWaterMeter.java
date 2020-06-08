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
@ApiModel(value = "用水量数据")
@TableName("dd_data_water_meter")
public class DdDataWaterMeter  implements Serializable {

    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "水表ID")
    private Integer deviceId;

    @ApiModelProperty(value = "总用水")
    private Float waterNum;

    @ApiModelProperty(value = "距上次用水")
    private Float thisNum;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
