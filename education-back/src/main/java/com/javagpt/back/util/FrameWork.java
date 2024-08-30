package com.javagpt.back.util;

import java.util.UUID;

/**
 * @author JavaEdge
 * @date 2024/8/24
 */
public class FrameWork {

    private static final ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 启动跟踪
     *
     * @param parentTraceId 父跟踪ID，如果为null则生成新的跟踪ID
     * @param source        来源
     */
    public static void startTrace(String parentTraceId, String source) {
        if (parentTraceId == null) {
            parentTraceId = generateTraceId();
        }
        TRACE_ID_THREAD_LOCAL.set(parentTraceId);
    }

    /**
     * 获取当前线程的跟踪ID
     *
     * @return 跟踪ID
     */
    public static String getTraceId() {
        return TRACE_ID_THREAD_LOCAL.get();
    }

    /**
     * 生成一个新的跟踪ID
     *
     * @return 新的跟踪ID
     */
    private static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 清理当前线程的跟踪ID
     */
    public static void clearTraceId() {
        TRACE_ID_THREAD_LOCAL.remove();
    }
}
