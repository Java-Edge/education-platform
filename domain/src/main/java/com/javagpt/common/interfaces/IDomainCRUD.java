package com.javagpt.common.interfaces;

/**
 * CRUD 接口定义或约束
 *
 * @param <DO> 领域对象泛型表达
 */
public interface IDomainCRUD<DO> extends IDomainSaveOrUpdate<DO>, IDomainQuery<DO>, IDomainDelete<DO> {

}
