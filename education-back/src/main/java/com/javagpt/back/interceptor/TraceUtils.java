package com.javagpt.back.interceptor;

import java.util.UUID;

/**
 * @author JavaEdge
 * @date 2024/1/11
 */
public class TraceUtils {
    public static String getTraceId() {
        // 生成一个唯一的TraceId
        return UUID.randomUUID().toString();
    }
}
