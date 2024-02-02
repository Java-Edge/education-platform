package com.javagpt.user.entity;

import com.icv.monitorplatform.domain.common.entity.BaseAuditEntity;
import com.icv.monitorplatform.domain.user.resporitory.SsoUserAttributeRepository;
import lombok.Data;

/**
 * sso用户的属性表
 *
 * @TableName t_sso_user_attribute
 */
@Data
public class SsoUserAttributeEntity extends BaseAuditEntity<SsoUserAttributeEntity, SsoUserAttributeRepository> {

    /**
     * sso中的用户id
     */
    private Long userId;

    /**
     * 属性类型 1、区域
     */
    private Integer attrType;

    /**
     * 属性值
     */
    private String attrValue;

}