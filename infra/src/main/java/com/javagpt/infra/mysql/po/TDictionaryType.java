package com.javagpt.infra.mysql.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.javagpt.infra.mysql.po.common.BaseAuditPO;
import lombok.Data;

import java.util.Dictionary;
import java.util.List;

/**
 * @author JavaEdge
 * @date 2024/3/5
 */
@TableName(value = "dictionary_type")
@Data
public class TDictionaryType  extends BaseAuditPO {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
