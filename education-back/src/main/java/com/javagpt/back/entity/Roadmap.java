package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName(value ="roadmap")
@Data
@EqualsAndHashCode(callSuper = false)
public class Roadmap implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String des;

    private String target;

    private String highlight;

    private BigDecimal price;

    private String lecturer;

    private String href;

    private String img;
}
