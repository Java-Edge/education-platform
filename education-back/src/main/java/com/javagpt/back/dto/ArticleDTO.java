package com.javagpt.back.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {


    /**
     * 职业主键
     */
    private Integer jobId;

    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 公司筛选条件
     */
    private List<String> company;
}
