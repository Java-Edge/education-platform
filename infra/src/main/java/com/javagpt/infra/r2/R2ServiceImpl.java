package com.javagpt.infra.r2;

import com.javagpt.common.oos.OssService;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

/**
 * @author JavaEdge
 * @date 2025/2/27
 */
@Slf4j
public class R2ServiceImpl implements OssService {

    private final S3Client s3Client;
    private final String bucketName;
    private final S3Presigner presigner;

    public R2ServiceImpl(R2Properties properties) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            properties.getAccessKeyId(),
            properties.getSecretAccessKey()
        );

        this.s3Client = S3Client.builder()
            .region(Region.of(properties.getRegion()))
            .endpointOverride(URI.create(properties.getEndpoint()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();

        this.presigner = S3Presigner.builder()
            .region(Region.of(properties.getRegion()))
            .endpointOverride(URI.create(properties.getEndpoint()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();

        this.bucketName = properties.getBucketName();
    }

    @Override
    public void uploadFile(InputStream inputStream, String objectName) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

            s3Client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
        } catch (Exception e) {
            log.error("上传文件到R2失败", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public URL downloadFile(String objectName) {
        try {
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(b -> b.bucket(bucketName).key(objectName))
                .signatureDuration(Duration.ofHours(1))
                .build();

            return presigner.presignGetObject(presignRequest).url();
        } catch (Exception e) {
            log.error("生成R2下载链接失败", e);
            throw new RuntimeException("获取下载链接失败");
        }
    }

    @Override
    public InputStream downloadFileStream(String objectName) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

            return s3Client.getObject(request);
        } catch (Exception e) {
            log.error("从R2下载文件流失败", e);
            throw new RuntimeException("下载文件失败");
        }
    }
}
