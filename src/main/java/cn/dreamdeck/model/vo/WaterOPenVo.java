package cn.dreamdeck.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "水表")
public class WaterOPenVo implements Serializable {

    private Integer deviceId;

    private Float waterNum;

    private Float humidity;

    private String createTime;


}
