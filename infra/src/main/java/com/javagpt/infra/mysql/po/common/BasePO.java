package com.javagpt.infra.mysql.po.common;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BasePO implements Serializable {

    /**
     * 主键id 自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 逻辑删除标记
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}