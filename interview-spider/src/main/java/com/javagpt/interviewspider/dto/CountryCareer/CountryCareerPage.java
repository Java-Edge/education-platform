package com.javagpt.interviewspider.dto.CountryCareer;

import lombok.Data;

import java.util.List;

/**
 * javagpt
 */
@Data
public class CountryCareerPage {

    /**
     * 当前页
     */
    private int page;

    /**
     * 页大小
     */
    private int pageSize;

    /**
     * 总数
     */
    private int total;

    /**
     * 招聘列表
     */
    private List<CountryCareerDTO> list;

}