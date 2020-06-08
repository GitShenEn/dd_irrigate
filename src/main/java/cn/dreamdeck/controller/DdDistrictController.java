package cn.dreamdeck.controller;


import cn.dreamdeck.model.entity.DdDistrict;
import cn.dreamdeck.model.entity.DdDistrictGroup;
import cn.dreamdeck.model.entity.DdGroup;
import cn.dreamdeck.service.DdDistrictGroupService;
import cn.dreamdeck.service.DdDistrictService;
import cn.dreamdeck.service.DdGroupService;
import cn.dreamdeck.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
@RequestMapping("district")
@Api(value = "district", tags = "分区管理")
public class DdDistrictController {


    private DdDistrictService districtService;

    private DdDistrictGroupService districtGroupService;

    private DdGroupService ddGroupService;


    /**
     * 分组全部列表
     * @return
     */
    @ApiOperation(value = "全部分区列表", notes = "全部分区列表")
    @GetMapping("/all")
    public R getGroupAll() {
        return R.ok(districtService.list(Wrappers.<DdDistrict>lambdaQuery()
                        .eq(DdDistrict::getDelFlag, "0")
                        .orderBy(true, false, DdDistrict::getCreateTime)
                )
        );
    }


    /**
     * 添加分区
     *
     * @param districtName 分区名称
     * @return
     */
    @ApiOperation(value = "添加分区", notes = "添加分区")
    @PostMapping("/save")
    public R add(String districtName) {
        if (StringUtils.isBlank(districtName)) {
            return R.failed("分区名称不能为空");
        }
        List<DdDistrict> list = districtService.list(Wrappers.<DdDistrict>lambdaQuery()
                .eq(DdDistrict::getDistrictName, districtName));
        if (list != null && list.size() > 0) {
            return R.failed("名称不能重复");
        }
        return R.ok(districtService.saveDistrict(districtName));
    }


    /**
     * 修改分区
     *
     * @param districtName 分区名称
     * @return
     */
    @ApiOperation(value = "修改分区", notes = "修改分区")
    @PostMapping("/update")
    public R update(Integer districtId, String districtName) {
        if (districtId==null) {
            return R.failed("分区ID不能为空");
        }
        if (StringUtils.isBlank(districtName)) {
            return R.failed("分区名称不能为空");
        }
        DdDistrict district =  districtService.getById(districtId);
        if (district==null){
            return R.failed("没有此记录");
        }
        district.setDistrictName(districtName);
        return R.ok(districtService.editDistrict(district));
    }


    /**
     * 删除分区
     *
     * @param districtId 分区ID
     * @return
     */
    @ApiOperation(value = "删除分区", notes = "删除分区")
    @PostMapping("/del")
    public R del(Integer districtId) {
        if (districtId==null) {
            return R.failed("分区ID不能为空");
        }
        districtGroupService.delByDis(districtId);
        return R.ok(districtService.deleteById(districtId));
    }

    /**
     * 获取分区下的组
     * @param districtId
     * @return
     */
    @ApiOperation(value = "获取分区下的组", notes = "获取分区下的组")
    @GetMapping("/getByDis")
    public R getByDis(Integer districtId) {
        return R.ok(districtGroupService.selectByDis(districtId));
    }

    /**
     * 向分区添加组
     * @param districtId
     * @param groupId
     * @return
     */
    @ApiOperation(value = "向分区添加组", notes = "向分区添加组")
    @PostMapping("/setGroup")
    public R setGroup(Integer districtId, Integer groupId){
        if (districtId==null){
            R.failed("分区ID不能为空");
        }
        if (groupId==null){
            R.failed("分组ID不为空");
        }
        DdDistrict district = districtService.getById(districtId);
        if (district==null){
            R.failed("没有此分区");
        }
        DdGroup group =  ddGroupService.getById(groupId);
        if (group==null){
            R.failed("没有此分组");
        }
        DdDistrictGroup districtGroup = districtGroupService.selectByGroup(groupId);
        if (districtGroup!=null){
            if (districtGroup.getDistrictId().equals(districtId)){
                return R.failed("已有记录");
            }else{
                districtGroupService.delById(districtGroup.getID());
            }
        }
        return  R.ok(districtGroupService.save(districtId,groupId));
    }


    /**
     * 从分区删除组
     * @param ID
     * @return
     */
    @ApiOperation(value = "从分区删除组", notes = "从分区删除组")
    @PostMapping("/delGroup")
    public R delGroup(Integer ID){
        if (ID==null){
            R.failed("记录ID不能为空");
        }
        return  R.ok(districtGroupService.delById(ID));
    }

    /**
     * 修改排序
     * @param ID   ID
     * @param rank 排序
     * @return
     */
    @ApiOperation(value = "修改排序", notes = "修改排序")
    @PostMapping("/setRank")
    public R setRank(Integer ID, Integer rank){
        if (ID==null){
            R.failed("记录ID不能为空");
        }
        if (rank==null){
            R.failed("排序不能为空");
        }
        return  R.ok(districtGroupService.setRank(ID,rank));
    }












}
