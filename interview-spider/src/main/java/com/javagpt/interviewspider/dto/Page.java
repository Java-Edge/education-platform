package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Data
public class Page<T> {

    /**
     * 当前页
     */
    private Integer current;

    /**
     * 页面大小
     */
    private Integer size;

    /**
     * 总数据
     */
    private Integer total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 数据详情
     */
    private List<T> records;

}
