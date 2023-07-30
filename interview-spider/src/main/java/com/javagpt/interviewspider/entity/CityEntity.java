package com.javagpt.interviewspider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 城市
 * @TableName city
 */
@TableName(value ="city")
@Data
public class CityEntity implements Serializable {

    /**
     * 城市编号
     */
    @TableId
    private Long id;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 归属省或者市级
     */
    private Long parentCode;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 是否热门城市
     */
    private Integer isHotCity;


    /**
     * 首字母
     */
    private String firstChar;

    /**
     * 城市等级
     */
    private Integer level;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}