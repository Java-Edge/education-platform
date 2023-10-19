package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zqy
 * @since 2023-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目标题
     */
    private String title;

    /**
     * 项目简介
     */
    private String des;

    /**
     * 项目架构图
     */
    private String architectureImg;

    /**
     * 通过项目将要学习到
     */
    private String willLearn;

    /**
     * 项目亮点
     */
    private String highlight;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer deleteFlag;

    private String href;

    private String img;
    private Integer pageView;
}
