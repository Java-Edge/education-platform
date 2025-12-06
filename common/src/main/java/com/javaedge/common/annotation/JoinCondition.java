package com.javaedge.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询注解
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinCondition {
    /**
     * left join的字段名称
     *
     * @return
     */
    String value() default "";

    /**
     * 字段的别名
     *
     * @return
     */
    String asResp() default "";

    /**
     * left join 表的名称
     *
     * @return
     */
    String table();

    /**
     * left join 表的字段
     *
     * @return
     */
    String field();

    /**
     * left join 主表的字段名称
     *
     * @return
     */
    String joinField();


}
