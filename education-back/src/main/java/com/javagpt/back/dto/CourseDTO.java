package com.javagpt.back.dto;

import lombok.Data;

@Data
public class CourseDTO {


    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     * 0， 首页课程
     * 1. 专栏
     */
    private Integer type;

}
