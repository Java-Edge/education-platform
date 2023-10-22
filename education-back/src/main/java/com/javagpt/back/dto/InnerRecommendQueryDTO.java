package com.javagpt.back.dto;

import lombok.Data;

import java.util.List;

@Data
public class InnerRecommendQueryDTO {

    /**
     * 职业主键
     */
    private Integer jobId;

    /**
     * 内容
     */
    private String content;

    /**
     * 搜索内容
     */
    private String keyword;

    /**
     * 公司筛选条件
     */
    private List<String> company;

    /**
     * 文章类型
     * 1 面经
     * 2 内推
     */
    private Integer articleType;
}
