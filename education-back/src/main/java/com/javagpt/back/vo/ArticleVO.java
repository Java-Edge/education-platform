package com.javagpt.back.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/27 21:31
 * @description this is a class file created by bubaiwantong in 2023/7/27 21:31
 */
@Data
public class ArticleVO {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 新标题
     */
    private String newTitle;

    /**
     * 内容
     */
    private String content;

    /**
     * 新内容
     */
    private String newContent;

    /**
     *
     */
    private String type;

    /**
     * 状态
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
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    /**
     * 职业主键
     */
    private Integer jobId;

    /**
     * 文字类型
     */
    private Integer contentType;

    /**
     * 职业名称
     */
    private String careerName;

    /**
     * 图片列表
     */
    private List<String> images;


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
