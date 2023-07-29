package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/29 23:31
 * @description this is a class file created by bubaiwantong in 2023/7/29 23:31
 */
@Data
public class BossResult<T> {

    private List<T> list;

}
