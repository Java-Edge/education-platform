package com.javagpt.user.dto;

import lombok.Data;

/**
 * @author bubaiwantong
 * @date 2023/8/5 18:50
 * @description this is a class file created by bubaiwantong in 2023/8/5 18:50
 */
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

}
