package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/24 23:02
 * @description this is a class file created by bubaiwantong in 2023/7/24 23:02
 */
@Data
public class ExperienceParam {


    /**
     * 公司主键，用于过滤
     */
    private List<Long> companyList;

    /**
     * 面试经验的类型，比如说Java工程师是11002
     */
    private Integer jobId;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 当前页面数
     */
    private Integer page;

    /**
     * 暂时没有合适解释
     */
    private Boolean isNewJob;


}
