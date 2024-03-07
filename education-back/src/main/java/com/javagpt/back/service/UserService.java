package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.UserPO;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.application.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService extends IService<UserPO> {

    /**
     * 检查用户名
     */
    int checkUsername(String username);

    /**
     * 新增用户
     */
    int insertUser(UserDTO user);

    /**
     * 根据用户名和密码查询用户
     */
    UserPO selectUser(String username, String md5);

    /**
     * 用户注册
     */
    ResultBody register(HttpServletRequest request, UserDTO user);

    /**
     * 用户登录
     */
    ResultBody doLogin(HttpServletRequest request,HttpServletResponse response, UserDTO user);

    /**
     * 获取验证码
     */
    void getCheckCode(HttpServletRequest request, HttpServletResponse response);

    /**
     * 登出
     */
    ResultBody logout(HttpServletRequest request, HttpServletResponse response);
}
