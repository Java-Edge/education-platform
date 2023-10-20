package com.javagpt.back.vo;

import lombok.Data;

import java.util.List;

/**
 * 菜单值对象
 */
@Data
public class MenuVO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 子菜单
     */
    private List<MenuVO> children;

    /**
     * 排序字段
     */
    private Integer sort;

}
