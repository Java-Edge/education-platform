package com.javagpt.interviewspider.dto.CountryCareer;

import lombok.Data;

/**
 * javagpt
 */
@Data
public class CountryCareerResultBody<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 未知信息
     */
    private String trace;

}
