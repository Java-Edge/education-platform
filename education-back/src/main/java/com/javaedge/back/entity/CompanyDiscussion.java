package com.javaedge.back.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyDiscussion implements Serializable {

    /**
     * 展示来源
     */
    private String authDisplayInfo;

    /**
     * 内容id
     */
    private String contentId;

    /**
     * 内容url
     */
    private String contentUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private String companyName;

    /**
     * 数据来源平台（牛客/知乎/脉脉）
     */
    private String source;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;
}
