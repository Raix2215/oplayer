package com.huangzizhu.util;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyuncs.exceptions.ClientException;
import com.huangzizhu.pojo.AliyunOSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.time.YearMonth;
import java.util.UUID;


@Slf4j
@Component
public class AliOSSOperator {
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    public String uploadFile(byte[] fileBytes , String filename) throws ClientException {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();
        OSS ossClient = getOss();
        YearMonth currentYearMonth = YearMonth.now();
        String objectName = currentYearMonth.toString() + "/" + UUID.randomUUID() + getFileExtension(filename);
        try {
            //上传文件
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(fileBytes));
            log.info("文件 " + objectName + " 上传成功。");
        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.info("Error Message:" + oe.getErrorMessage());
            log.info("Error Code:" + oe.getErrorCode());
            log.info("Request ID:" + oe.getRequestId());
            log.info("Host ID:" + oe.getHostId());
        } catch (com.aliyun.oss.ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.info("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return endpoint.replace("https://","https://"+bucketName+".") + "/" + objectName;
    }
    /**
     * 获取文件后缀的方法
     * @param fileName 文件对象
     * @return 文件后缀，如果没有后缀则返回空字符串
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null) {
            return ""; // 如果文件不存在，返回空字符串
        }
        int dotIndex = fileName.lastIndexOf('.');
        // 如果文件名中没有点（.），或者点在文件名的开头（隐藏文件），则没有后缀
        if (dotIndex == -1 || dotIndex == 0) {
            return "";
        }
        return fileName.substring(dotIndex); // 返回后缀部分
    }

    private OSS getOss() throws ClientException {
        // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        // 创建 OSSClient 实例
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(aliyunOSSProperties.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .region(aliyunOSSProperties.getRegion())
                .build();
        return ossClient;
    }


}
