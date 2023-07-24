package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
@Data
public class UserBrief {

    private List<Object> activityIconList;
    private Boolean admin;
    private String authDisplayInfo;
    private Object badgeIconUrl;
    private String educationInfo;
    private Object enterpriseInfo;
    private Boolean followed;
    private String gender;
    private String headDecorateUrl;
    private String headImgUrl;
    private Long honorLevel;
    private String honorLevelColor;
    private String honorLevelName;
    private Object identityList;
    private Object member;
    private Object memberEndTime;
    private Long memberIdentity;
    private Object memberStartTime;
    private String nickname;
    private Long userId;
    private String workTime;


}