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
     * 岗位描述
     */
    private String des;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 公司编号
     */
    private Integer companyId;

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
     * 刷新时间
     */
    private Date refeshTime;

    /**
     * 
     */
    private Integer feedBackDays;

    /**
     * 
     */
    private Integer durationDays;

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
     * 项目编号
     */
    private Integer projectId;

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