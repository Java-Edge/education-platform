package com.javagpt.interviewspider.controller;

import com.javagpt.interviewspider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bubaiwantong
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {


    @Autowired
    private SpiderService spiderService;

    @GetMapping("/test")
    public void test(){
        spiderService.work();
    }

}
