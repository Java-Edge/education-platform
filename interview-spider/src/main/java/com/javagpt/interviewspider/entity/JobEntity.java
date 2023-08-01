package com.javagpt.interviewspider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 职位分类表
 * @TableName job
 */
@TableName(value ="job")
@Data
public class JobEntity implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 父主键
     */
    private Integer parentId;

    /**
     * 创建者主键
     */
    private Integer creatorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
