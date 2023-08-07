package com.javagpt.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
/**
 * @author JavaEdge
 */
public class ConvertUtils {

    public static String objToString(Object value) {
        if (value == null) {
            return null;
        }
        String str;
        if (value instanceof String) {
            str = (String) value;
        } else if (value instanceof Number || value instanceof Boolean) {
            str = String.valueOf(value);
        } else if (value instanceof Date) {
            str = DateUtil.formatDateTime((Date) value);
        } else if (value instanceof LocalDateTime) {
            str = LocalDateTimeUtil.formatNormal((LocalDateTime) value);
        } else {
            str = toJsonString(value);
        }
        return str;
    }

    private static String toJsonString(Object value) {
        return JSON.toJSONString(value);
    }

    public static <T> T strToObject(String str, Class<T> tClass) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        if (String.class.isAssignableFrom(tClass)) {
            return (T) str;
        } else if (Byte.class.isAssignableFrom(tClass)) {
            return (T) Byte.valueOf(str);
        } else if (Short.class.isAssignableFrom(tClass)) {
            return (T) Short.valueOf(str);
        } else if (Integer.class.isAssignableFrom(tClass)) {
            return (T) Integer.valueOf(str);
        } else if (Long.class.isAssignableFrom(tClass)) {
            return (T) Long.valueOf(str);
        } else if (Float.class.isAssignableFrom(tClass)) {
            return (T) Float.valueOf(str);
        } else if (Double.class.isAssignableFrom(tClass)) {
            return (T) Double.valueOf(str);
        } else if (Date.class.isAssignableFrom(tClass)) {
            return (T) DateUtil.parse(str);
        } else if (LocalDateTime.class.isAssignableFrom(tClass)) {
            return (T) LocalDateTimeUtil.parse(str);
        } else {
            return toBean(str, tClass);
        }
    }

    private static <T> T toBean(String str, Class<T> tClass) {
        return JSON.parseObject(str, tClass);
    }

    public static <T> List<T> toList(String result, Class<T> tClass) {
        return JSONArray.parseArray(result, tClass);
    }

    /**
     * 将origin的非null字段属性，复制到bean里面
     *
     * @param origin
     * @param bean
     */
    public static <T> T fillBeanWithObject(Object origin, T bean) {
        if (origin == null) {
            return bean;
        }
        BeanUtil.fillBeanWithMap(ModelUtils.beanToMap(origin), bean, true);
        return bean;
    }
}
