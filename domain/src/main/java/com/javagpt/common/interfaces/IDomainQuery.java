package com.javagpt.common.interfaces;

/**
 * 接口：查询方法
 *
 */
public interface IDomainQuery<DO> {
    DO fetchOne();
}
