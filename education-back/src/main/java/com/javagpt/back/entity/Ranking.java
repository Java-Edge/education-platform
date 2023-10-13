package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 千祎来了
 * @date 2023/8/8 15:14
 */
@TableName(value ="ranking")
@Data
public class Ranking implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String des;

    private Integer orderVal;

    private String href;

    private String img;

    private Integer pageView;

}
