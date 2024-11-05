package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zqy
 * @since 2023-10-16
 */
@Data
public class PermissionConfig {

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

    private String des;

}
