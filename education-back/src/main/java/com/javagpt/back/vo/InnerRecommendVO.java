package com.javagpt.back.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class InnerRecommendVO {
    /**
     * 主键
     */
    private String id;

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
     * 内推码
     */
    private String recommendCode;

    /**
     * 内推邮箱
     */
    private String recommendEmail;

    /**
     * 内推链接
     */
    private String recommendUrl;
}
