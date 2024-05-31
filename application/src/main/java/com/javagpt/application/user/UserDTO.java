package com.javagpt.application.user;

import lombok.Data;

@Data
public class UserDTO {

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 验证码
     */
    private String validCode;

    /**
     *
     */
    private Boolean ok;

    /**
     * 信息
     */
    private String message;

    /**
     * token
     */
    private String token;
}
