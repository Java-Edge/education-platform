package com.javaedge.back.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "course_category")
@Data
public class CourseCategory {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer category;
}