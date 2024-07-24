package com.javagpt.common.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 处理课程内容的下载，例如视频或PDF材料
 *
 * @author JavaEdge
 * @date 2024/7/19
 */
public final class CourseContentDownloadTask implements Subtask<byte[]> {

    /**
     * 跟踪下载任务的当前状态
     */
    private State currentState = State.UNAVAILABLE;

    private Throwable downloadException = null;

    private final String contentUrl;

    public CourseContentDownloadTask(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    /**
     * 返回下载任务的当前状态（如，SUCCESS、FAILED或UNAVAILABLE）
     * @return
     */
    @Override
    public State state() {
        return currentState;
    }

    /**
     * 返回执行下载操作的Callable
     * @return
     */
    @Override
    public Callable<byte[]> task() {
        return () -> {
            // Placeholder for actual download logic
            // This could involve making an HTTP request to `contentUrl` and downloading the content
            // For simplicity, this is just a stub
            return new byte[0];
        };
    }

    /**
     * 启动下载任务并在完成时返回结果
     * @return
     */
    @Override
    public byte[] get() {
        Future<byte[]> future = Executors.newSingleThreadExecutor().submit(task());
        try {
            byte[] content = future.get();
            currentState = State.SUCCESS;
            return content;
        } catch (InterruptedException | ExecutionException e) {
            currentState = State.FAILED;
            downloadException = e;
            return null;
        }
    }

    /**
     * 返回下载过程中遇到的任何异常
     * @return
     */
    @Override
    public Throwable exception() {
        return downloadException;
    }


}

