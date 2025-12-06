package com.javaedge.application.user;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    private String password;


    /**
     * 验证码
     */
    @NotBlank
    private String validCode;

    /**
     * token
     */
    private String token;
}
