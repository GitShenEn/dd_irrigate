package cn.dreamdeck.service;


import cn.dreamdeck.model.entity.DdDistrict;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDistrictService extends IService<DdDistrict> {

    Boolean saveDistrict(String districtName);

    Boolean editDistrict(DdDistrict district);

    Boolean deleteById(Integer districtId);
}
