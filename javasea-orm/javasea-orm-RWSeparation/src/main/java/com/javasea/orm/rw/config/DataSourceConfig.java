package com.javasea.orm.rw.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * 注释：
 *
 * @author 陈导
 * @date 2020/8/3 14:40
 */
@Configuration
public class DataSourceConfig {

    /** 创建可读数据源，方式一 */
    @Bean("selectDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.select")
//    @Qualifier("selectDataSource")
    public DataSource selectDataSource(){
        return DataSourceBuilder.create().build();
    }

    /** 创建可写数据源，方式二 */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.update")
    @Qualifier("updateDataSource")
    public DataSource updateDataSource(){
        return DataSourceBuilder.create().build();
    }

}
