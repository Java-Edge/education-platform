package com.javagpt.back.controller;

import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Company;
import com.javagpt.back.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("getList")
    public ResultBody getCompanyNames() {
        List<String> list = companyService.getCompanyNames();
        return ResultBody.success(list);
    }

}

