package com.iee.file.propsLoad.other.map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 通用的配置文件加载方式,不同的配置文件, 加载成一个独立的bean, 各个独立的bean可以出现key相同的情况
 * @Author longxiaonan@163.com
 * @Date 18:20 2018/7/23
 **/
@Configuration
public class AppPropertiesConfigure {

    @Bean("aaProp")
    public AppProperties getAaProp() {
        return AppProperties.getInst("aa.properties");//aa.yml加载不了的
    }

    @Bean("bbProp")
    public AppProperties getBbProp() {
        return AppProperties.getInst("bb.properties");
    }

}
