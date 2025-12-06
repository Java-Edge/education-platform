package com.javaedge.back.service.impl;

import com.javaedge.back.entity.CompanyNature;
import com.javaedge.back.mapper.CompanyNatureMapper;
import com.javaedge.back.service.CompanyNatureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.vo.CompanyNatureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyNatureServiceImpl extends ServiceImpl<CompanyNatureMapper, CompanyNature> implements CompanyNatureService {


    @Autowired
    private CompanyNatureMapper companyNatureMapper;

    @Override
    public List<CompanyNatureVO> getList() {
        return companyNatureMapper.getList();
    }
}
