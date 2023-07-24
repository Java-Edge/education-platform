package com.javagpt.back.dto;

import lombok.Data;

/**
 * @author bubaiwantong
 * @date 2023/7/24 23:42
 * @description this is a class file created by bubaiwantong in 2023/7/24 23:42
 */
@Data
public class PageQueryParam<T> {

    /**
     * 当前页
     */
    private int pageNo;

    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 查询参数
     */
    private T param;


}
