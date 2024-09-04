package com.javagpt.infra.bos;

import com.aliyun.oss.OSS;
import com.javagpt.common.oos.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author sss
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
    public void uploadFile(InputStream inputStream, String key) {
        ossClient.putObject(bucketName, key, inputStream);
    }
}