package com.javagpt.back.vo;

import lombok.Data;

@Data
public class ArticleVO {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    private Integer pageView;
}
