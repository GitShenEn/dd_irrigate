package cn.dreamdeck.controller;


import cn.dreamdeck.model.dto.WarnDto;
import cn.dreamdeck.service.DdWarnLogService;
import cn.dreamdeck.utils.DateUtil;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("warn")
@Api(value = "warn", tags = "告警管理")
public class DdWarnController {

    private DdWarnLogService warnLogService;

    /**
     * 全部设备列表
     * @return
     */
    @ApiOperation(value = "告警列表", notes = "告警列表")
    @GetMapping("/list")
    public R getList(Page page, WarnDto warnDto){
        return R.ok(warnLogService.getList(page,warnDto));
    }

    /**
     * 全部设备列表
     * @return
     */
    @ApiOperation(value = "告警个数", notes = "告警个数")
    @GetMapping("/getNum")
    public R getNum(){
        Map map = new HashMap();
        map.put("sum",warnLogService.getNum(null));
        map.put("last",warnLogService.getNum(DateUtil.getDay()));
        return  R.ok(map);
    }


    /**
     * 告警人工处理
     * @return
     */
    @ApiOperation(value = "告警人工处理", notes = "告警人工处理")
    @GetMapping("/dispose")
    public R dispose(Integer logId){
        if (logId==null){
            return  R.failed("记录ID不能为空");
        }
        return  R.ok(warnLogService.dispose(logId));
    }


}
