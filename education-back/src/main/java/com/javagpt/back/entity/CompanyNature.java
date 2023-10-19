package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyNature implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司性质id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 公司性质名
     */
    private String name;

    /**
     * 公司性质简称
     */
    private String shortName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
