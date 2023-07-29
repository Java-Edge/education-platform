package com.javagpt.interviewspider.data.nowcoder;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Data
public class MomentData {

    private Boolean beMyOnly;
    private Object circle;
    private Object clockMoment;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 已经编辑
     */
    private Boolean hasEdit;

    /**
     * 主键
     */
    private Long id;

    /**
     * 图片列表
     */
    private List<ImageMoment> imgMoment;

    /**
     * ip4地址
     */
    private String ip4;

    /**
     * ip4位置
     */
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