package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqy
 * @since 2023-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoadMapDetail implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 路线详情描述标题
     */
    private String title;

    /**
     * 路线详情描述
     */
    private String des;

    /**
     * 学习目标
     */
    private String target;

    /**
     * 课程重点
     */
    private String courseFocus;

    /**
     * 课程标签，存储标签id，逗号隔开
     */
    private String tag;

    /**
     * 类型：0 根据外链跳转 ，1 根据课程id跳转
     */
    private Integer type;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 详情所在路线id
     */
    private Integer roadMapId;

    /**
     * 详情在学习路线中的顺序
     */
    private Integer mapOrder;

    @TableField(exist = false)
    private List<String> tags;

    @TableField(exist = false)
    private CourseEntity course;
}
