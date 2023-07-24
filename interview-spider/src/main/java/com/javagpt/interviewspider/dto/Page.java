package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by bubaiwantong in 2023/7/23 0:01
 */
@Data
public class Page<T> {

    private Integer current;

    private Integer size;


    private Integer total;

    private List<T> records;

}
