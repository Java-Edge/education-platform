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
    private String id;

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
     * 是否匿名标志
     */
    private Integer isAnonymousFlag;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 职位id，属于哪一个岗位的面经
     */
    private Integer jobId;

    /**
     * 文章类型
     */
    private Integer contentType;

    /**
     *牛客网的文章主键
     */
    private Long sourceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
