package com.javaedge.back.controller;

import com.javaedge.common.resp.ResultBody;
import com.javaedge.application.user.UserDTO;
import com.javaedge.back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    @PostMapping("/register")
    public ResultBody register(HttpServletRequest request, @RequestBody UserDTO user) {
        try {
            return userService.register(request, user);
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return ResultBody.error("注册失败，请稍后再试");
        }
    }

    @PostMapping("/doLogin")
    public ResultBody login(HttpServletRequest request,HttpServletResponse response, @RequestBody UserDTO user) {
        return userService.doLogin(request,response, user);
    }

    @GetMapping("/checkUsername")
    public ResultBody checkUsername(@RequestParam String username) {
        return ResultBody.success(userService.checkUsername(username));
    }

    /**
     * description: 获取验证码图片
     **/
    @GetMapping("/getCheckCode")
    public void getCheckCode(HttpServletResponse response, HttpServletRequest request) {
        userService.getCheckCode(request, response);
    }

    @GetMapping("/logout")
    public ResultBody logout(HttpServletResponse response, HttpServletRequest request) {
        return userService.logout(request, response);
    }

    @MessageMapping("hello")
    public Mono<String> hello(String input) {
        return Mono.just("Hello: " + input);
    }
}