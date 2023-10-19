package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String typeKey;

    /**
     * 字典名称
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 最小值
     */
    private Integer minValue;

    /**
     * 最大值
     */
    private Integer maxValue;

    /**
     * 0:删除，1：正常，2：不可用
     */
    private Integer status;

    private LocalDateTime createTime;

    /**
     * 排序
     */
    private Integer sort;

    private Long parentId;


}
