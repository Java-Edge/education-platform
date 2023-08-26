package com.javagpt.back.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class RecruitVO {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
