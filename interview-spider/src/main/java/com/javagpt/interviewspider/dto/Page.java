package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author bubaiwantong
 * @Date 2023/7/24 17:40
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: Page
 * @Version 1.0
 */
@Data
public class Page<T> {

    private Integer current;

    private Integer size;


    private Integer total;

    private List<T> records;

}
