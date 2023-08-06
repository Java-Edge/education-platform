package com.javagpt.base.controller;


import com.javagpt.base.dto.ResultBody;
import com.javagpt.base.entity.UserEntity;
import com.javagpt.base.service.UserService;
import com.javagpt.common.util.Md5Util;
import com.javagpt.common.util.VerifyCodeUtil;
import com.javagpt.base.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public ResultBody register(HttpServletRequest request, @RequestBody UserDTO user) {
        String verifyCode = ( String ) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)) {
            user.setOk(false);
            user.setMessage("验证码输入错误！");
            return ResultBody.success(user);
        }
        user.setId(String.valueOf(new Date().getTime()));
        user.setPassword(Md5Util.getMD5(user.getPassword()));
        int num = userService.insertUser(user);
        if (num <= 0) {
            return ResultBody.error("注册失败！");
        }
        user.setMessage("注册成功！");
        return ResultBody.success(user);
    }

    @RequestMapping("/login")
    public ResultBody login(HttpServletRequest request, @RequestBody UserDTO user) {
        String verifyCode = ( String ) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)){
            user.setOk(false);
            user.setMessage("验证码输入错误！");
            return ResultBody.success(user);
        }
        UserEntity userEntity = userService.selectUser(user.getUsername(), Md5Util.getMD5(user.getPassword()));
        if (userEntity == null) {
           return ResultBody.error("登录失败");
        }
        return  ResultBody.success(user);
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
        try {
            int width = 120;
            int height = 40;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = VerifyCodeUtil.drawRandomText(width, height, verifyImg);
            request.getSession().setAttribute("verifyCode", randomText);
            request.getSession().setAttribute("startTime",new Date());
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
            OutputStream os = response.getOutputStream(); //获取文件输出流
            ImageIO.write(verifyImg, "png", os);//输出图片流
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
            log.error("获取验证码图片失败!", e);
        }
    }

}

