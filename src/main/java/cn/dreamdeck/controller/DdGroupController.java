package cn.dreamdeck.controller;



import cn.dreamdeck.model.entity.DdDeviceWater;
import cn.dreamdeck.model.entity.DdDistrictGroup;
import cn.dreamdeck.model.entity.DdGroup;
import cn.dreamdeck.model.entity.DdGroupWater;
import cn.dreamdeck.service.DdDeviceWaterService;
import cn.dreamdeck.service.DdDistrictGroupService;
import cn.dreamdeck.service.DdGroupService;
import cn.dreamdeck.service.DdGroupWaterService;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("group")
@Api(value = "group", tags = "分组管理")
public class DdGroupController {

    private DdGroupService ddGroupService;

    private DdDeviceWaterService ddDeviceWaterService;

    private DdGroupWaterService groupWaterService;

    private DdDistrictGroupService districtGroupService;


    /**
     * 分组分页列表
     *
     * @param page
     * @return
     */
    @ApiOperation(value = "分组分页列表", notes = "分组分页列表")
    @GetMapping("/page")
    public R getGroupPage(Page page, Integer type) {
        return R.ok(ddGroupService.getGroupPage(page, type));
    }

    /**
     * 分组全部列表
     *
     * @param type (1:已经分组，2：未分组,null为全部)
     * @return
     */
    @ApiOperation(value = "全部分组列表", notes = "全部分组列表")
    @GetMapping("/all")
    public R getGroupAll(Integer type) {
        return R.ok(ddGroupService.selectAll(type));
    }

    /**
     * 添加分组
     *
     * @param groupName
     * @return
     */
    @ApiOperation(value = "添加分组", notes = "添加分组")
    @PostMapping("/add")
    public R saveGroup(String groupName) {
        if (StringUtils.isBlank(groupName)) {
            return R.failed("分组名称不能为空");
        }
        List<DdGroup> list = ddGroupService.list(Wrappers.<DdGroup>lambdaQuery()
                .eq(DdGroup::getName, groupName)
                .eq(DdGroup::getDelFlag,"0"));
        if (list != null && list.size() > 0) {
            return R.failed("名称不能重复");
        }
        return R.ok(ddGroupService.saveGroup(groupName));
    }

    /**
     * 修改分组
     *
     * @param groupId
     * @param groupName
     * @return
     */
    @ApiOperation(value = "修改分组", notes = "修改分组")
    @PostMapping("/update")
    public R editGroup(Integer groupId, String groupName) {
        if (groupId == null) {
            return R.failed("分组ID不能为空");
        }
        if (StringUtils.isBlank(groupName)) {
            return R.failed("分组名称不能为空");
        }
        List<DdGroup> list = ddGroupService.list(Wrappers.<DdGroup>lambdaQuery()
                .eq(DdGroup::getName, groupName)
                .eq(DdGroup::getDelFlag,"0"));
        if (list != null && list.size() > 0) {
            return R.failed("名称不能重复");
        }
        return R.ok(ddGroupService.editGroup(groupId, groupName));
    }

    /**
     * 删除分组
     *
     * @param groupId
     * @return
     */
    @ApiOperation(value = "删除分组", notes = "删除分组")
    @PostMapping("/del")
    public R delete(Integer groupId) {
        if (groupId == null) {
            return R.failed("ID不能为空");
        }
        List<DdDistrictGroup> list = districtGroupService.list(Wrappers.<DdDistrictGroup>lambdaQuery()
                .eq(DdDistrictGroup::getGroupId, groupId));
        for (DdDistrictGroup group : list) {
            districtGroupService.delById(group.getID());
        }
        groupWaterService.delByGroup(groupId);
        return R.ok(ddGroupService.deleteById(groupId));
    }


    /**
     * 查询分组里面的水表
     *
     * @param groupId
     * @return
     */
    @ApiOperation(value = "查询分组里面的水表", notes = "查询分组里面的水表")
    @GetMapping("/ByGroup")
    public R ByGroup(Integer groupId) {
        return R.ok(groupWaterService.selectByGroup(groupId));
    }


    /**
     * 向分组里面添加水表
     *
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "向分组添加水表", notes = "向分组添加水表")
    @PostMapping("/setWater")
    public R setWater(Integer groupId, Integer deviceId) {
        DdGroup group = ddGroupService.getById(groupId);
        if (group == null) {
            return R.failed("没有此分组");
        }
        DdDeviceWater water = ddDeviceWaterService.getById(deviceId);
        if (water == null) {
            return R.failed("没有此水表");
        }
        DdGroupWater groupWater = groupWaterService.selectByDev(deviceId);
        if (groupWater != null) {
            if (groupWater.getGroupId() == groupId) {
                return R.failed("已有记录");
            } else {
                groupWater.setGroupId(groupId);
                return R.ok(groupWaterService.updateById(groupWater));
            }
        }
        return R.ok(groupWaterService.save(groupId, deviceId));
    }

    /**
     * 从分组删除水表
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "从分组删除水表", notes = "从分组删除水表")
    @PostMapping("/delWater")
    public R delWater(Integer id) {
        DdGroupWater water = groupWaterService.getById(id);
        if (water == null) {
            return R.failed("没有此记录");
        }
        return R.ok(groupWaterService.deleteById(id));
    }


}
