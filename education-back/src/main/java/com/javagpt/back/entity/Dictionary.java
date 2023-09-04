package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
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
