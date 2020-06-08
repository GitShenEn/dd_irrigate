package cn.dreamdeck.model.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设备状态返回")
public class DeviceStatusVo {

    public DeviceStatusVo(){
        this.setOnlineRate("0");
        this.setOfflineRate("0");
        this.setSum(0);
        this.setOnline(0);
        this.setOffline(0);
    }

    @ApiModelProperty(value = "在线率")
    private String onlineRate;

    @ApiModelProperty(value = "离线率")
    private String offlineRate;

    @ApiModelProperty(value = "设备总数")
    private Integer sum;

    @ApiModelProperty(value = "在线个数")
    private Integer online;

    @ApiModelProperty(value = "离线个数")
    private Integer offline;


}
