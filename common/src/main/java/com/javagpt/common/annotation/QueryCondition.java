package com.javagpt.common.annotation;

import com.javagpt.common.constant.QueryEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCondition {
    /**
     * 查询类型
     *
     * @return
     */
    QueryEnum value() default QueryEnum.AUTO;

    /**
     * left join 查询
     *
     * @return
     */
    JoinCondition leftJoin() default @JoinCondition(table = "", field = "", joinField = "");


    AndOrCondition andOr() default @AndOrCondition();

    Class<?> InClassType() default Integer.class;

}
