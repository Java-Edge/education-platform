package com.javagpt.back.dto;


import lombok.Data;

import java.util.List;

@Data
public class SpecialQueryDTO {

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


    /**
     * 专栏类别
     */
    private Integer category;

    /**
     * 专栏类别
     */
    private List<Integer> categories;

    private Integer itemId;

}
