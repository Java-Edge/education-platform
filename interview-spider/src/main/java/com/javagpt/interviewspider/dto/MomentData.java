package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
@Data
public class MomentData {

    private Boolean beMyOnly;
    private Object circle;
    private Object clockMoment;
    private String content;
    private Long createdAt;
    private Long editTime;
    private Boolean hasEdit;
    private Long id;
    private List<ImageMoment> imgMoment;
    private String ip4;
    private String ip4Location;
    private Boolean isAnonymousFlag;
    private Object linkMoment;
    private Object newContent;
    private Object newTitle;
    private Long status;
    private String title;
    private Long type;
    private Long userId;
    private String uuid;
    private Object videoMoment;


}