package com.javaedge.back.controller;


import com.alibaba.fastjson.JSONArray;
import com.javaedge.common.resp.ResultBody;
import com.javaedge.back.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * @author zqy
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/career")
public class CareerController {

    @Autowired
    private CareerService careerService;

    /**
     * 返回多级联查所需格式
     * @return
     */
    @GetMapping("/getData")
    public ResultBody getData() {
        JSONArray result = careerService.getData();
        return ResultBody.success(result);
    }


}

