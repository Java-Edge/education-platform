package com.javagpt.interviewspider.entity;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
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