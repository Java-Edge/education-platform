package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.dto.UserDTO;
import com.javagpt.back.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
* @author MSIK
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-08-05 18:49:02
*/
public interface UserService extends IService<UserEntity> {

    /**
     * 检查用户名
     *
     * @param username
     * @return
     */
    int chkUsername(String username);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    int insertUser(UserDTO user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param md5
     * @return
     */
    UserEntity selectUser(String username, String md5);

    /**
     * 用户注册
     *
     * @param request
     * @param user
     */
    ResultBody register(HttpServletRequest request, UserDTO user);

    /**
     * 用户登录
     *
     * @param request
     * @param user
     * @return
     */
    ResultBody doLogin(HttpServletRequest request, UserDTO user);

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     */
    void getCheckCode(HttpServletRequest request, HttpServletResponse response);
}
