package com.javagpt.interviewspider.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @Author JavaGPT
 * @Date 2023/7/26 16:17
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: CareerJobSelector
 */

@Data
public class CareerJobSelector {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 菜单标签等级
     */
    private Integer level;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 是否用户职业
     */
    private Boolean isUserCareerJob;

    /**
     * 子菜单
     */
    private List<CareerDTO> childCareerJobs;

}
