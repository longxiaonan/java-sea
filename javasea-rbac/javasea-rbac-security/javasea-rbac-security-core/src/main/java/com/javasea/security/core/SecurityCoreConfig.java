package com.javasea.security.core;

import com.javasea.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SecurityCoreConfig
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/28 0028 22:58
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
