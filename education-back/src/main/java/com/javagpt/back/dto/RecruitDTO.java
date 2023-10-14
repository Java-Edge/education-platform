package com.javagpt.back.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 招聘-职位
 * @TableName recruit
 */
@ApiModel
@Data
public class RecruitDTO implements Serializable {

    /**
     * 招聘所属类型
     */
    private Integer type;

    /**
     * 招聘类型 校招、实习、社招
     */
    private Integer recruitType;

    /**
     * 工作城市
     */
    private String jobCity;

    /**
     * 工作地点
     */
    private String jobAddress;

    /**
     * 职位号
     */
    private Integer careerJobId;

    /**
     * 职位名称
     */
    private String careerJobName;

    /**
     * 毕业年份
     */
    private String graduateYear;

    /**
     * 薪资范围
     */
    private Integer salaryRange;

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
     * 学历要求
     */
    private Integer eduLevel;

    /**
     * 公司规模
     */
    private Integer personScale;

    /**
     * 融资规模
     */
    private Integer scaleTag;
}