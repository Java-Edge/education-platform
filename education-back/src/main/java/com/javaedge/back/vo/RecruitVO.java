package com.javaedge.back.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RecruitVO {

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 公司编号
     */
    private String companyId;

    /**
     * 招聘类型 校招、实习、社招
     */
    private Integer recruitType;

    /**
     * 招聘名称
     */
    private String jobName;

    /**
     * 额外信息
     */
    private String ext;

    /**
     * 工作城市
     */
    private String jobCity;

    /**
     * 职位号
     */
    private Long careerJobId;

    /**
     * 毕业年份
     */
    private String graduationYear;

    /**
     * 招聘开始时间
     */
    private Date deliverBegin;

    /**
     * 招聘结束时间
     */
    private Date deliverEnd;

    /**
     *
     */
    private Integer durationDays;

    /**
     * 薪水类型
     */
    private Integer salaryType;

    /**
     * 薪资最小值
     */
    private Integer salaryMin;

    /**
     * 薪资最大值
     */
    private Integer salaryMax;

    /**
     *
     */
    private Integer salaryMonth;

    /**
     *
     */
    private Integer workYearType;

    /**
     * 学历
     */
    private Integer eduLevel;


    /**
     * 公司图片
     */
    private String picUrl;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 规模标签
     */
    private String scaleTagName;

    /**
     * 人员范围
     */
    private String personScales;

    /**
     * 公司简称
     */
    private String companyShortName;

    /**
     * 原始招聘链接
     */
    private String sourceUrl;

    private Integer pageView;
}
