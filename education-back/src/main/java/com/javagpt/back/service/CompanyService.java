package com.javagpt.back.service;

import com.javagpt.back.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 公司主体 服务类
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
public interface CompanyService extends IService<Company> {

    List<String> getCompanyNames();
}