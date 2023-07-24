package com.javagpt.interviewspider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName interview_experience_article
 */
@TableName(value ="interview_experience_article")
@Data
public class InterviewExperienceArticleEntity implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String newTitle;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String newContent;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String hasEdit;

    /**
     * 
     */
    private Integer isAnonymousFlag;

    /**
     * 
     */
    private Date createAt;

    /**
     * 
     */
    private Date editTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}