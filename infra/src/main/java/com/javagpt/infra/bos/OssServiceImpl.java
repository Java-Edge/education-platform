package com.javagpt.infra.bos;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.AddBucketReplicationRequest;
import com.javagpt.common.oos.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * @author sss
 * @date 2023/03/20
 */
@Slf4j
@Service
public class OssServiceImpl implements OssService {

    public static void main(String[] args) throws Exception {
        String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
        // 填写源Bucket名称，例如src-bucket。
        String bucketName = "javaedge";
        // 目标Bucket
        String targetBucketName = "codeselect";
        // 目标Bucket地域
        String targetBucketLocation = "oss-cn-shanghai";

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, "", "");

        try {
            AddBucketReplicationRequest request = new AddBucketReplicationRequest(bucketName);
            request.setTargetBucketName(targetBucketName);
            request.setTargetBucketLocation(targetBucketLocation);
            // 表示禁止复制历史数据。
            request.setEnableHistoricalObjectReplication(false);
            // 指定授权OSS进行数据复制的角色名称，且该角色必须已被授予源Bucket执行跨区域复制以及目标Bucket接收复制对象的权限。
            request.setSyncRole("root");
            //List prefixes = new ArrayList();
            //prefixes.add("image/");
            //prefixes.add("video");
            //prefixes.add("a");
            //prefixes.add("A");
            // 指定待复制Object的前缀Prefix。指定Prefix后，只有匹配该Prefix的Object才会复制到目标Bucket。
            //request.setObjectPrefixList(prefixes);
            //List actions = new ArrayList();
            //actions.add(AddBucketReplicationRequest.ReplicationAction.ALL);
            // 指定可以被复制到目标Bucket的操作。默认值为ALL，表示源Bucket的所有操作都会复制到目标Bucket。
            //request.setReplicationActionList(actions);
            ossClient.addBucketReplication(request);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

}
