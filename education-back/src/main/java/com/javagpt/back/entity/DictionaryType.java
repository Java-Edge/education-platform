package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictionaryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典类型标识
     */
    private String typeKey;

    /**
     * 0:删除，1：正常
     */
    private Integer status;

    /**
     * 描述
     */
    private String des;

    /**
     * 字典集合
     */
    @TableField(exist = false)
    private List<Dictionary> list;

}
