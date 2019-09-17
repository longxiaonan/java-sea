package com.iee.spring.configdemo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName tes2
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/7/31 17:22
 */
@Configuration
public class ConfigDemo {
    /**
     * @Description 在后台，使用标准的@Configuration类实现自动配置。当应该应用自动配置时，
     * 可以使用附加的@条件注解来约束。通常，自动配置类使用@ConditionalOnClass和@ConditionalOnMissingBean注解。
     * 这确保只有在找到相关类和没有声明自己的@Configuration时才应用自动配置。
     * @Author longxiaonan@163.com
     * @Date 17:38 2018/7/31
     **/
    @Bean
    @ConditionalOnClass(Test1.class)
    Test3 tes3(Test1 test) {
        System.err.println(test);
        return new Test3();
    }


}
