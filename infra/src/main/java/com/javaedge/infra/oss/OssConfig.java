package com.javaedge.infra.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import com.javaedge.common.oos.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Bean
    public OSS ossClient() throws ClientException {
        // 设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, credentialsProvider);
    }

    @Bean
    OssService ossService(OSS ossClient) {
        return new OssServiceImpl(ossClient, bucketName);
    }
}