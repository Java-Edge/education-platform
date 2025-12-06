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
}
