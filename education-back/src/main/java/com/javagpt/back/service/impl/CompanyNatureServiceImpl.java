package com.javagpt.back.service.impl;

import com.javagpt.back.entity.CompanyNature;
import com.javagpt.back.mapper.CompanyNatureMapper;
import com.javagpt.back.service.CompanyNatureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.vo.CompanyNatureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
@Service
public class CompanyNatureServiceImpl extends ServiceImpl<CompanyNatureMapper, CompanyNature> implements CompanyNatureService {


    @Autowired
    private CompanyNatureMapper companyNatureMapper;

    @Override
    public List<CompanyNatureVO> getList() {
        return companyNatureMapper.getList();
    }
}
