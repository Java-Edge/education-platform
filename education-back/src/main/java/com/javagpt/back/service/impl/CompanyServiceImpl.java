package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Company;
import com.javagpt.back.mapper.CompanyMapper;
import com.javagpt.back.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.common.enums.CompanyNatureEnums;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public List<String> getCompanyNames() {
        QueryWrapper<Company> qw = new QueryWrapper<>();
        qw.eq("company_nature_id", CompanyNatureEnums.ENTERPRIZE.getCode());
        qw.select("company_name");
        List<Company> courseCategories = this.getBaseMapper().selectList(qw);
        return courseCategories.stream()
                .map(Company::getCompanyName)
                .toList();
    }
}