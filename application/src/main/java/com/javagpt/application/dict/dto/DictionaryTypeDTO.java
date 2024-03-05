package com.javagpt.application.dict.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.javagpt.common.req.BaseAuditBean;

import java.util.Dictionary;
import java.util.List;

/**
 * @author JavaEdge
 * @date 2024/3/5
 */
public class DictionaryTypeDTO extends BaseAuditBean {

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
