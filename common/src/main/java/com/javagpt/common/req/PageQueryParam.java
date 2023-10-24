package com.javagpt.common.req;

import lombok.Data;

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
