package com.javagpt.back.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjectVO {

    /**
     * 项目id
     */
    private Integer id;

    /**
     * 项目标题
     */
    private String title;

    /**
     * 项目简介
     */
    private String des;

    private String href;

    private String img;

    private Integer pageView;
}
