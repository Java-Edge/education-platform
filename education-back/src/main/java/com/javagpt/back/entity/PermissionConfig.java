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
 * @since 2023-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置路径
     */
    private String path;

    /**
     * 配置类型：1 需登陆
     */
    private Integer type;


}
