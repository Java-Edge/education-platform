package com.javagpt.common.oos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author JavaEdge
 */
public interface OssService {

    /**
     * 简单的上传文件
     *
     * @param inputStream 文件流
     * @param key         目录名称+文件名称
     */
    default void uploadFile(InputStream inputStream, String key) {

    }


    /**
     * 下载文件到指定路径
     *
     * @param key
     * @param outputFile
     * @throws IOException
     */
    default void downloadFile(String key, File outputFile) {

    }

    /**
     * 简单流式下载
     *
     * @param key BOS服务器的目录名称+文件名称
     * @return 流
     */
    default InputStream downloadFile(String key) {
        return null;
    }

    default InputStream downloadFile2(String key, long start, long end) {
//        // 获取Object，返回结果为BosObject对象
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//        getObjectRequest.setRange(start, end);
//        BosObject object = bosClient.getObject(getObjectRequest);
//        // 获取Object的输入流
//        return object.getObjectContent();
        return null;
    }
}
