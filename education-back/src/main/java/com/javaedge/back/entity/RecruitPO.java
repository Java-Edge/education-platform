package com.javaedge.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位表字段
 */
@TableName(value = "recruit")
@Data
public class RecruitPO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 岗位信息
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 岗位要求
     */
    private String requirements;

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
     * 原始网站招聘链接
     */
    private String sourceUrl;

    /**
     * 类别信息
     */
    private String category;

    /**
     * 专业信息
     */
    private String major;

    /**
     * 浏览量
     */
    private Integer pageView;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}