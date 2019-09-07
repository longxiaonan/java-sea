package com.javasea.web.aspect.aop;

import com.javasea.web.aspect.aop.Audience;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AudienceConfig {
    @Bean
    public Audience audience(){
        return new Audience();
    }

    @Bean
    public Advicer advicer(){
        return new Advicer();
    }
}
