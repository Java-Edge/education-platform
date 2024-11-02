package com.javagpt.common.oos;

import com.aliyun.oss.model.ObjectMetadata;

import java.io.InputStream;
import java.net.URL;

/**
 * @author JavaEdge
 */
public interface OssService {

    /**
     * 简单的上传文件
     *
     * @param inputStream 文件流
     * @param objectName 目录名称+文件名称
     */
    default void uploadFile(InputStream inputStream, String objectName) {

    }

    /**
     * 简单下载
     *
     * @param objectName 目录名称+文件名称
     * @return 流
     */
     default URL downloadFile(String objectName) {
        return null;
    }

    /**
     * 简单流式下载
     *
     * @param objectName 目录名称+文件名称
     * @return 流
     */
    default InputStream downloadFileStream(String objectName) {
        return null;
    }

    default InputStream downloadVideo(String key, long start, long end) {
//        // 获取Object，返回结果为BosObject对象
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//        getObjectRequest.setRange(start, end);
//        BosObject object = bosClient.getObject(getObjectRequest);
//        // 获取Object的输入流
//        return object.getObjectContent();
        return null;
    }
}