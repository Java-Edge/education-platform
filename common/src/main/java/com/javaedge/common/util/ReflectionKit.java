package com.javaedge.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author JavaEdge
 * @date 2024/9/3
 */
public class ReflectionKit {

    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                if (!(params[index] instanceof Class)) {
                    return Object.class;
                } else {
                    return (Class)params[index];
                }
            } else {
                return Object.class;
            }
        }
    }
}
