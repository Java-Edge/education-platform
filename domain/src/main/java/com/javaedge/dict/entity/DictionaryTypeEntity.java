package com.javaedge.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.javaedge.common.entity.BaseAuditEntity;
import com.javaedge.dict.repository.DictTypeRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Dictionary;
import java.util.List;

/**
 * 字典表
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictionaryTypeEntity extends BaseAuditEntity<DictionaryTypeEntity, DictTypeRepository> {

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
