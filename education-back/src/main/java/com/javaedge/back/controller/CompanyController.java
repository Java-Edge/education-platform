package com.javaedge.back.controller;

import com.javaedge.common.resp.ResultBody;
import com.javaedge.back.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公司控制器
 * 使用 Service 层的缓存机制，无需在 Controller 层维护本地缓存
 *
 * @author zqy
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 获取公司列表
     * 数据通过 Service 层的 @Cacheable 注解进行缓存
     * 首次请求会查询数据库，后续请求直接从缓存获取
     * 缓存配置：1小时写入过期，30分钟访问过期
     */
    @GetMapping("/getList")
    public ResultBody getList(){
        return ResultBody.success(companyService.getCompanyNames());
    }
}

