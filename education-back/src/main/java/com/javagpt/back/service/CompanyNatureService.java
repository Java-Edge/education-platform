package com.javagpt.back.service;

import com.javagpt.back.entity.CompanyNature;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.CompanyNatureVO;

import java.util.List;

public interface CompanyNatureService extends IService<CompanyNature> {

    List<CompanyNatureVO> getList();

}
