package com.javaedge.common.util;

import javassist.Modifier;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author JavaEdge
 */
public class ReflectionUtils {

    /**
     * 获取对象的所有字段，包括父类
     */
    public static List<Field> getAllFields(Object o) {
        Class<?> tempReqClass = o.getClass();
        return getAllFields(tempReqClass);
    }

    /**
     * 获取类的所有字段，包括父类
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        Class<?> tempReqClass = clazz;
        List<Field> allFields = new ArrayList<>();
        Set<String> tempFieldNameSet = new HashSet<>();
        while (tempReqClass != null && Object.class != tempReqClass) {
            List<Field> fields = Arrays.asList(tempReqClass.getDeclaredFields());
            // JDK 16+ Stream#toList: 返回不可变列表，避免无谓的收集器对象创建。
            List<Field> addFields = fields.stream()
                    .filter(it -> !Modifier.isStatic(it.getModifiers()))
                    .filter(it -> !tempFieldNameSet.contains(it.getName()))
                    .toList();
            allFields.addAll(addFields);
            // 这里不需要 Set 语义，后续仅用于 contains 检查。
            tempFieldNameSet.addAll(fields.stream().map(Field::getName).toList());
            tempReqClass = tempReqClass.getSuperclass();
        }
        return allFields;
    }

    /**
     * 设置拷贝的对象的特定字段的值为null
     */
    public static List<String> setNullValueForOverrideFields(Object source, Object target, String[] overrideFields) {
        if (overrideFields == null || overrideFields.length == 0) {
            return null;
        }
        // JDK 9+ List.of: 明确不可变，避免意外修改调用方传入数组语义。
        List<String> fields = List.of(overrideFields);
        List<String> setNullFields = new ArrayList<>();
        List<String> resultSetNullFields = new ArrayList<>();
        List<Field> allFields = getAllFields(source);
        for (Field sourceField : allFields) {
            String sourceFieldName = sourceField.getName();
            if (fields.contains(sourceFieldName)) {
                sourceField.setAccessible(true);
                try {
                    Object fieldVal = sourceField.get(source);
                    // JDK 16+ instanceof 模式匹配：减少显式强转与重复变量。
                    if (fieldVal instanceof String s) {
                        if (StringUtils.isBlank(s)) {
                            setNullFields.add(sourceFieldName);
                        }
                    } else if (fieldVal == null) {
                        setNullFields.add(sourceFieldName);
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
