package com.javagpt.back.controller;

import com.javagpt.common.resp.ResultBody;
import com.javagpt.application.user.UserDTO;
import com.javagpt.back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文件描述: 用户登录，注册
 *
 * @author yuzonghao
 * @date 2020/05/22 14:43
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ResultBody register(HttpServletRequest request, @RequestBody UserDTO user) {
        return userService.register(request,user);
    }

    @RequestMapping("/doLogin")
    public ResultBody login(HttpServletRequest request,HttpServletResponse response, @RequestBody UserDTO user) {
        return userService.doLogin(request,response, user);
    }

    @GetMapping("/checkUsername")
    public ResultBody checkUsername(@RequestParam String username) {
        return ResultBody.success(userService.checkUsername(username));
    }

    /**
     * description: 获取验证码图片
     * param [response, request]
     * author yuzonghao
     * createTime 2020/3/7 13:36
     **/
    @GetMapping("/getCheckCode")
    public void getCheckCode(HttpServletResponse response, HttpServletRequest request) {
        userService.getCheckCode(request, response);
    }

    @GetMapping("/logout")
    public ResultBody logout(HttpServletResponse response, HttpServletRequest request) {
        return userService.logout(request, response);
    }
}