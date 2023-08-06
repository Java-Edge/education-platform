package com.javagpt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日期查询结束
 * 比较数据库的字段，比较格式为 <
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeEnd {
    /**
     * 字段名称,此名称为model的字段里面的名称
     *
     * @return
     */
    String value();
}
