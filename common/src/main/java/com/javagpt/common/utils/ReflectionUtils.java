package com.javagpt.common.utils;


import javassist.Modifier;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JavaEdge
 */
public class ReflectionUtils {

    /**
     * 获取对象的所有字段，包括父类
     *
     * @param o
     * @return
     */
    public static List<Field> getAllFields(Object o) {
        Class tempReqClass = o.getClass();
        return getAllFields(tempReqClass);
    }

    /**
     * 获取类的所有字段，包括父类
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        Class tempReqClass = clazz;
        List<Field> allFields = new ArrayList<>();
        Set<String> tempFieldNameSet = new HashSet<>();
        while (tempReqClass != null && Object.class != tempReqClass) {
            List<Field> fields = Arrays.asList(tempReqClass.getDeclaredFields());
            List<Field> AddFields = fields.stream().filter(it -> !Modifier.isStatic(it.getModifiers())).filter(it -> !tempFieldNameSet.contains(it.getName())).collect(Collectors.toList());
            allFields.addAll(AddFields);
            tempFieldNameSet.addAll(fields.stream().map(it -> it.getName()).collect(Collectors.toSet()));
            tempReqClass = tempReqClass.getSuperclass();
        }
        return allFields;
    }

    /**
     * 设置拷贝的对象的特定字段的值为null
     *
     * @param source
     * @param target
     * @param overrideFields
     */
    public static List<String> setNullValueForOverrideFields(Object source, Object target, String[] overrideFields) {
        if (overrideFields == null || overrideFields.length == 0) {
            return null;
        }
        List<String> fields = Arrays.asList(overrideFields);
        List<String> setNullFields = new ArrayList<>();
        List<String> resultSetNullFields = new ArrayList<>();
        List<Field> allFields = getAllFields(source);
        for (Field sourceField : allFields) {
            String sourceFieldName = sourceField.getName();
            if (fields.contains(sourceFieldName)) {
                sourceField.setAccessible(true);
                try {
                    Object fieldVal = sourceField.get(source);
                    if (fieldVal instanceof String) {
                        if (StringUtils.isBlank((String) fieldVal)) {
                            setNullFields.add(sourceFieldName);
                        }
                    } else {
                        if (fieldVal == null) {
                            setNullFields.add(sourceFieldName);
                        }
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }

        if (setNullFields.isEmpty()) {
            return null;
        }
        List<Field> targetFields = getAllFields(target);
        for (Field targetField : targetFields) {
            String targetFieldName = targetField.getName();
            if (setNullFields.contains(targetFieldName)) {
                targetField.setAccessible(true);
                try {
                    targetField.set(target, null);
                    resultSetNullFields.add(targetFieldName);
                } catch (IllegalAccessException e) {
                }
            }
        }
        return resultSetNullFields;
    }

}
