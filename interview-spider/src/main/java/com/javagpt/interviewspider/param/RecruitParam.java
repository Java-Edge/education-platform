package com.javagpt.interviewspider.param;

import lombok.Data;

/**
 * @Author JavaGPT
 * @Date 2023/8/1 14:54
 * @PackageName:com.javagpt.interviewspider.param
 * @ClassName: RecruitParam
 * @Version 1.0
 */
@Data
public class RecruitParam {

    private Integer requestForm;

    private Integer page;

    private Integer pageSize;

    private Integer recruitType;

    private Integer pageSource;

    private String visitorId;
}
