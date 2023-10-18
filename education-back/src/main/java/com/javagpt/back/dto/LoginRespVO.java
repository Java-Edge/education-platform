package com.javagpt.back.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户实体参数")
public class LoginRespVO implements Serializable {

    /**
     * 用户编号
     */
    private Integer id;

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
