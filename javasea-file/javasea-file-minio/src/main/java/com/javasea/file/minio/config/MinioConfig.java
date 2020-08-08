package com.javasea.file.minio.config;

import com.javasea.file.minio.entity.MinioData;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio客户端配置
 * @date：2020年6月7日
 */
@Slf4j
@Configuration
public class MinioConfig {

    @Autowired
    private MinioData minioData;

    /**
     * 在这个配置类里面，是可以拓展的minioClient的，编写自己喜欢的方法，这里就直接采用原来的，其实都是一样的，
     * 只不过有些人喜欢按照自己的风格重写一遍而已。
     * @author 溪云阁
     * @return MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        try {
            return new MinioClient(minioData.getUrl(), minioData.getAccessKey(), minioData.getSecretKey());
        }
        catch (final Exception e) {
            log.error("初始化minio出现异常:{}", e.fillInStackTrace());
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

}
