package com.javagpt.back.service.impl;

import com.javagpt.back.entity.Company;
import com.javagpt.back.mapper.CompanyMapper;
import com.javagpt.back.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司主体 服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
