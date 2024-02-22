package com.javagpt.user.resporitory;

import com.javagpt.user.entity.SsoUserAttributeEntity;

import java.util.List;

/**
 * @author JavaEdge
 * @date 2023/3/15
 */
public interface SsoUserAttributeRepository extends IBaseRepository<SsoUserAttributeEntity> {

    List<SsoUserAttributeEntity> findByUserIdAndAttrType(Long userId, Integer attrType);

    void deleteByUserIdAndAttrType(Long userId, Integer attrType);
}
