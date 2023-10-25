package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="inner_recommend")
@Data
public class InnerRecommend implements Serializable {
    /**
     * 主键
     */
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

    /**
     * 职位id
     */
    private Integer jobId;

    /**
     * 文章类型
     */
    private Integer contentType;

    /**
     * 浏览次数
     */
    private Integer pageView;

    // 面经分类
    @TableField(exist = false)
    private String careerName;

    /**
     * 文章类型
    private Integer articleType;

    /**
     * 内推码
     */
    private String recommendCode;

    /**
     * 内推邮箱
     */
    private String recommendEmail;

    /**
     * 内推链接
     */
    private String recommendUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}