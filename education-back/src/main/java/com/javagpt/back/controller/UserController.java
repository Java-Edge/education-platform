package com.javagpt.back.controller;


import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.dto.UserDTO;
import com.javagpt.back.entity.UserEntity;
import com.javagpt.back.service.UserService;
import com.javagpt.common.util.Md5Util;
import com.javagpt.common.util.VerifyCodeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

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

    @RequestMapping("/login")
    public ResultBody login(HttpServletRequest request, @RequestBody UserDTO user) {
        return userService.doLogin(request,user);
    }

    @GetMapping("/checkUsername")
    public ResultBody checkUsername(@RequestParam String username) {
        if (userService.chkUsername(username)){
            return ResultBody.success("1");
        }
        return ResultBody.success("0");
    }

    /**
     * description: 获取验证码图片
     * param [response, request]
     * author yuzonghao
     * createTime 2020/3/7 13:36
     **/
    @GetMapping("/getCheckCode")
    public void getCheckCode(HttpServletResponse response, HttpServletRequest request) {
        userService.getCheckCode(request,response);
    }

}

