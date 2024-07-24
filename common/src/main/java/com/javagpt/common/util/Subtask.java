package com.javagpt.common.util;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author JavaEdge
 * @date 2024/7/19
 */
public sealed interface Subtask<T> extends Supplier<T> permits CourseContentDownloadTask {
    enum State {SUCCESS, FAILED, UNAVAILABLE}

    State state();

    Callable<? extends T> task();

    T get();

    Throwable exception();
}
