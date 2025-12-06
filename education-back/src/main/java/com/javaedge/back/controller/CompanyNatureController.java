package com.javaedge.back.controller;

import com.javaedge.common.resp.ResultBody;
import com.javaedge.back.service.CompanyNatureService;
import com.javaedge.back.vo.CompanyNatureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author zqy
 * @since 2023-08-10
 */
@RestController
@RequestMapping("/company-nature")
public class CompanyNatureController {

    @Autowired
    private CompanyNatureService companyNatureService;

    @GetMapping("/getList")
    public ResultBody getList(){
        List<CompanyNatureVO> list = companyNatureService.getList();
        return ResultBody.success(list);
    }
}

