package com.javagpt.common.annotation;

import java.lang.annotation.*;

/**
 * 注解此类的返回值会自动注入 ResponseVo
 *
 * @author JavaEdge
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RespSuccess {
}
