package com.javaedge.common.annotation;

import com.javaedge.common.constant.QueryEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询注解
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AndOrCondition {

    QueryEnum value() default QueryEnum.AUTO;
    /**
     * multiValue为true的时候的分割符号
     *
     * @return
     */
    String delimiter() default ",";

    /**
     * true 表示一个字段查询多个值，false表示一个多个字段查询一个值
     * 如果要想查多个值以逗号分割的字段，数据前后都要加","。比如1,2 那么数据存的格式为,1,2,
     * @return
     */
    boolean multiValue() default true;

    String[] fields() default "{}";


}
