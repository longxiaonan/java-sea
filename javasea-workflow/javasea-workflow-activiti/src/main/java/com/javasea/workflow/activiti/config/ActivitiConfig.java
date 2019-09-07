package com.javasea.workflow.activiti.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName ActivitiConfig
 * @Description
 * @Author longxiaonan@163.com
 * @Date 2019/7/12 0012 15:39
 */
@Configuration
public class ActivitiConfig {
    /**
     * @Description 单独的启动方式，用于创建表结构
     * @Author longxiaonan@163.com
     * @Date 17:15 2019/7/12 0012
     **/
    @Bean("standaloneProcessEngineConfiguration")
    public StandaloneProcessEngineConfiguration getStandaloneProcessEngineConfiguration(DataSource dataSource){
        StandaloneProcessEngineConfiguration config = new StandaloneProcessEngineConfiguration();
        config.setDataSource(dataSource);
        //true 创建表结构；false 不创建表机构
        config.setDatabaseSchemaUpdate("true");
        return config;
    }

    @Bean("springProcessEngineConfiguration")
    public SpringProcessEngineConfiguration getSpringProcessEngineConfiguration(DataSource dataSource){
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(dataSource);
        //true 创建表结构；false 不创建表机构
        config.setDatabaseSchemaUpdate("true");
        return config;
    }

}
