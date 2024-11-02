package com.javagpt.infra.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.javagpt.common.oos.OssService;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author JavaEdge
 * @date 2023/03/20
 */
@Slf4j
public class OssServiceImpl implements OssService {

    private OSS ossClient;

    private String bucketName;

    public OssServiceImpl(OSS ossClient, String bucketName) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
    }

    @Override
    public void uploadFile(InputStream inputStream, String objectName) {
        ossClient.putObject(bucketName, objectName, inputStream);
    }

    @Override
    public URL downloadFile(String objectName) {
        URL signedUrl = null;
        try {
            // 指定生成的签名URL过期时间，单位为毫秒。本示例以设置过期时间为1小时为例。
            Date expiration = new Date(new Date().getTime() + 3600 * 1000L);

            // 生成签名URL。
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
            // 设置过期时间。
            request.setExpiration(expiration);

            // 通过HTTP GET请求生成签名URL。
            signedUrl = ossClient.generatePresignedUrl(request);
            return signedUrl;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            throw new RuntimeException("Oss下载文件失败");
        }
    }

    @Override
    public InputStream downloadFileStream(String objectName) {
        try {
            // 该实例包含文件内容及文件元数据
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            // 获取文件输入流，可读取此输入流获取其内容
            return ossObject.getObjectContent();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            throw new RuntimeException("Oss下载文件失败");
        }
    }
}