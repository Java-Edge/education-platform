package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqy
 * @since 2023-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoadMap implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 学习步骤
     */
    private Integer step;

    /**
     * 需要学习课程数量
     */
    private Integer course;

    /**
     * 收藏人数
     */
    private Integer collect;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 封面
     */
    private String img;


}
