package cn.dreamdeck.service.impl;

import cn.dreamdeck.mapper.DdDistrictMapper;
import cn.dreamdeck.model.CommonConstants;
import cn.dreamdeck.model.entity.DdDistrict;
import cn.dreamdeck.service.DdDistrictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDistrictServiceImpl  extends ServiceImpl<DdDistrictMapper, DdDistrict> implements DdDistrictService {
    @Override
    public Boolean saveDistrict(String districtName) {
        DdDistrict district = new DdDistrict();
        district.setDistrictName(districtName);
        this.baseMapper.insert(district);
        return Boolean.TRUE ;
    }

    @Override
    public Boolean editDistrict(DdDistrict district) {
        this.baseMapper.updateById(district);
        return Boolean.TRUE ;
    }

    @Override
    public Boolean deleteById(Integer districtId) {
        DdDistrict district = this.baseMapper.selectById(districtId);
        if (district!=null){
            district.setDelFlag(CommonConstants.STATUS_DEL);
            this.baseMapper.updateById(district);
            return Boolean.TRUE ;
        }
        return Boolean.FALSE ;
    }
}

