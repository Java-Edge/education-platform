package com.javagpt.common.interfaces;

/**
 * 接口：新增或更新方法
 */
public interface IDomainSaveOrUpdate<DO> extends IDomainSave<DO>, IDomainUpdate<DO> {
    DO saveOrUpdate();
}
