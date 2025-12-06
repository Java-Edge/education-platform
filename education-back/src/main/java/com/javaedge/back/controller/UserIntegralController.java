package com.javaedge.back.controller;

import com.javaedge.back.service.UserIntegralService;
import com.javaedge.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 *  前端控制器
 *
 * @author zqy
 * @since 2023-10-26
 */
@RestController
@RequestMapping("/userIntegral")
public class UserIntegralController {

    @Autowired
    private UserIntegralService userIntegralService;

    @GetMapping("/sign/{userId}")
    public ResultBody sign(@PathVariable Integer userId) {
        return userIntegralService.sign(userId);
    }
}