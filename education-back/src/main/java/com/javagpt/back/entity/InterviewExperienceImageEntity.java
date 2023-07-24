package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 面试经验图片表
 * @TableName interview_experience_image
 */
@TableName(value ="interview_experience_image")
@Data
public class InterviewExperienceImageEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 图片路径
     */
    private String src;

    /**
     * 图片名称
     */
    private String alt;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 文章id
     */
    private Long recordId;

    /**
     * 图片顺序
     */
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}