package com.javagpt.interviewspider.param;

import lombok.Data;

import java.util.List;

/**
 * javagpt
 */
@Data
public class CountryCareerParam {

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 项目列表，用于请求不同的项目招聘信息
     */
    private List<String> projectId;

    /**
     * 来源
     */
    private String source;

    /**
     * 其他请求参数一并加入...
     */

}
