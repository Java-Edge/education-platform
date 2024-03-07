package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;

@TableName(value = "source_course")
@Data
public class CoursePO {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String image;

    /**
     *
     */
    private String description;


    /**
     *
     */
    private Date createTime;


    /**
     *
     */
    private String remark;

    /**
     *
     */
    private String sourceUrl;
    private Integer parentId;
    private Integer pageView;
    /**
     * 0: 视频  1:专栏
     */
    private Integer type;
}