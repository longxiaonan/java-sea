package com.javasea.file.minio.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * minio属性文件
 * @date：2020年6月7日
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "javasea.module.minio")
public class MinioData {

    /**
     * minio地址+端口号
     */
    private String url;

    /**
     * minio用户名
     */
    private String accessKey;

    /**
     * minio密码
     */
    private String secretKey;

    /**
     * 文件桶的名称
     */
    private String bucketName;

}
