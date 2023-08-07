package com.javagpt.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JavaEdge
 * @date 2023/4/12
 */
public class MyStringUtils {


    public static final String DELIMITER = ",";

    public static String appendCommaAround(String str) {
        return appendAround(str, DELIMITER);
    }

    /**
     * input: xx ,
     * output: ,xx,
     *
     * @param str
     * @param delimiter
     * @return
     */
    public static String appendAround(String str, String delimiter) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        str = StringUtils.strip(str, delimiter);
        str = delimiter + str + delimiter;
        return str;
    }


    public static String trimComma(String str) {
        return trim(str, DELIMITER);
    }

    public static List<String> trimCommaAndToList(String str) {
        String trim = trim(str, DELIMITER);
        if (StringUtils.isBlank(trim)) {
            return new ArrayList<>();
        }
        return Arrays.asList(trim.split(DELIMITER));
    }

    public static String trim(String str, String delimiter) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return StringUtils.strip(str, delimiter);
    }

    public static List<String> split(String value, String delimiter) {
        if (StringUtils.isBlank(value)) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        String[] splits = value.split(delimiter);
        for (String split : splits) {
            if (StringUtils.isNotBlank(split)) {
                result.add(split);
            }
        }
        return result;
    }

    public static <T> List<T> toList(String val, Class<T> tClass) {
        List<String> items = split(val, DELIMITER);
        List<T> result = new ArrayList<>();
        for (String item : items) {
            if (Integer.class.isAssignableFrom(tClass)) {
                result.add((T) Integer.valueOf(item));
            } else if (Long.class.isAssignableFrom(tClass)) {
                result.add((T) Long.valueOf(item));
            } else {
                result.add((T) item);
            }
        }
        return result;
    }
}
