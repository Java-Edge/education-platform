package com.javagpt.common.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 分布式不安全的id生成器
 *
 * @author JavaEdge
 * @date 2023/3/24
 */
public class IdUnsafeGenerator {
    private static final AtomicLong order = new AtomicLong(0L);
    private static final long start = 1679574435082L;

    private static final int SHIFT_BITS = 10;

    private static final int LOW_MAX_VALUE = 2 << 10;

    public static long nextId() {
        long x = order.getAndIncrement() % LOW_MAX_VALUE;
        long timeStamp = System.currentTimeMillis() - start;
        return timeStamp << SHIFT_BITS | x;
    }

}
