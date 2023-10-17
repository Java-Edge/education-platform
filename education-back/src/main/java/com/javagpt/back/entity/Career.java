package com.javagpt.back.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Career implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
     * 父类别
     */
    private Integer parentId;

    /**
     * 是否抓取
     */
    private Integer isGrab;
}
