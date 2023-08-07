package com.javagpt.common.bos;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author sss
 */
public interface BosService {

    /**
     * 简单的上传文件
     *
     * @param inputStream 文件流
     * @param key         目录名称+文件名称
     */
    void uploadFile(InputStream inputStream, String key);


    /**
     * 下载文件到指定路径
     *
     * @param key
     * @param outputFile
     * @throws IOException
     */
    void downloadFile(String key, File outputFile);

    /**
     * 简单流式下载
     *
     * @param key BOS服务器的目录名称+文件名称
     * @return 流
     */
    InputStream downloadFile(String key);
}
