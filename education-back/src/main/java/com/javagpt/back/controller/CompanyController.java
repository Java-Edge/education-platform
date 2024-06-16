package com.javagpt.back.controller;

import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.service.CompanyService;
import jakarta.annotation.PostConstruct;
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

    private List<String> list;

    @GetMapping("/getList")
    public ResultBody getList(){
        return ResultBody.success(list);
    }

    @PostConstruct
    public void init() {
        list = companyService.getCompanyNames();
    }
}

