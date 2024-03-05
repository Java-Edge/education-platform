package com.javaedge.common.factory;

import com.javagpt.common.exception.BusinessRuntimeException;
import com.javagpt.common.util.ApplicationUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 仓储工厂用来统一获取仓储实现
 */
public class RepositoryFactory {

    /**
     * 根据仓储接口类型获取对应实现且默认取值第一个
     *
     * @param tClass 目标仓储接口类型
     * @param <T>    目标类型
     * @return 如果不是指定实现，默认获得第一个实现Bean
     */
    public static <T> T get(Class<? extends T> tClass) {

        Map<String, ? extends T> map = ApplicationUtils.getApplicationContext().getBeansOfType(tClass);
        Collection<? extends T> collection = map.values();
        if (collection.isEmpty()) {
            throw BusinessRuntimeException.error("未找到仓储接口或其指定的实现:" + tClass.getSimpleName());
        }
        return collection.stream().findFirst().get();
    }
}