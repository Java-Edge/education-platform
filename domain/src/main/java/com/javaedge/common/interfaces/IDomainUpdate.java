package com.javaedge.common.interfaces;

/**
 * 接口：新增或更新方法
 */
@FunctionalInterface
public interface IDomainUpdate<DO> {
    DO update();
}
