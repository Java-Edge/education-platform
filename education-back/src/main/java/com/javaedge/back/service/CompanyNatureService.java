package com.javaedge.back.service;

import com.javaedge.back.entity.CompanyNature;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.back.vo.CompanyNatureVO;

import java.util.List;

public interface CompanyNatureService extends IService<CompanyNature> {

    List<CompanyNatureVO> getList();

}
