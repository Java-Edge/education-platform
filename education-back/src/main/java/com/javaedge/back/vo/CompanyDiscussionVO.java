package com.javaedge.back.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author robot
 * @since 2023-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyDiscussionVO implements Serializable {

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
