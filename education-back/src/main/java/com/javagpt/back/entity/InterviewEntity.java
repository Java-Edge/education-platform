package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@TableName(value = "interview_experience_article")
@Data
public class InterviewEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String content;


    private Integer jobId;

    // 面经分类
    @TableField(exist = false)
    private String careerName;

    /**
     *
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    private Integer pageView;
}