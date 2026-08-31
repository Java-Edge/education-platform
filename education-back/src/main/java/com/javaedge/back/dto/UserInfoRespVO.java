package com.javaedge.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoRespVO implements Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String avatar;
}
