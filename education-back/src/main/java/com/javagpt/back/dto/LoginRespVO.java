package com.javagpt.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRespVO implements Serializable {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户真实名称
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 令牌
     */
    private String token;
}
