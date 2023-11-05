package com.javagpt.back.controller;


import com.javagpt.back.service.UserIntegralService;
import com.javagpt.common.resp.ResultBody;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
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

