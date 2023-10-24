package com.javagpt.back.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleVO {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;


    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 职业主键
     */
    private Integer jobId;

    /**
     * 文字类型
     */
    private Integer contentType;

    /**
     * 职业名称
     */
    private String careerName;
}
