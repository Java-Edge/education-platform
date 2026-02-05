package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javaedge.back.entity.Company;
import com.javaedge.back.mapper.CompanyMapper;
import com.javaedge.back.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.common.enums.CompanyNatureEnums;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司服务实现类
 *
 * @author zqy
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    /**
     * 获取公司名称列表
     * 使用本地缓存，缓存名称为 companyNames
     * 由于该方法没有参数，所有请求共享同一个缓存
     */
    @Override
    @Cacheable(value = "companyNames", key = "'allCompanies'")
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