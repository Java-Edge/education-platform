package com.javagpt.back.dto;

import lombok.Data;

/**
 * @author bubaiwantong
 * @date 2023/7/24 23:44
 * @description this is a class file created by bubaiwantong in 2023/7/24 23:44
 */
@Data
public class InterviewArticleDto {


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


}
