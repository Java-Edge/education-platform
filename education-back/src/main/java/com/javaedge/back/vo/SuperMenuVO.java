package com.javaedge.back.vo;

import lombok.Data;

import java.util.List;

@Data
public class SuperMenuVO {


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
    private List<SuperMenuVO> children;

    /**
     * 菜单等级：1-一级菜单，2-二级菜单，3-三级菜单
     */
    private Integer level;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 父节点
     */
    private Integer parentId;

}
