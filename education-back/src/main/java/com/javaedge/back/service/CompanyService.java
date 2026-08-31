package com.javaedge.back.service;

import com.javaedge.back.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 公司主体 服务类
 */
public interface CompanyService extends IService<Company> {

    List<String> getCompanyNames();
}