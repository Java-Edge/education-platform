package com.javagpt.interviewspider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName career
 */
@TableName(value ="career")
@Data
public class CareerEntity implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}