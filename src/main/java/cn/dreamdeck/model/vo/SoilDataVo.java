package cn.dreamdeck.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "土壤传感器返回数据")
public class SoilDataVo {

    @ApiModelProperty(value = "设备ID")
    private Integer deviceId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "关联水表")
    private Integer waterId;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "温度")
    private Float temperature;

    @ApiModelProperty(value = "湿度")
    private Float humidity;

    @ApiModelProperty(value = "盐分")
    private Float salinity;

    public SoilDataVo(){
        this.setHumidity(0f);
        this.setTemperature(0f);
        this.setSalinity(0f);
    }

}
