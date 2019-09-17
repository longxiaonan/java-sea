
package com.zhirui.lmwy.wms.config;

import com.zhirui.lmwy.wms.security.interceptor.JwtInterceptor;
import com.zhirui.lmwy.wms.security.interceptor.PermissionInterceptor;
import com.zhirui.lmwy.wms.security.interceptor.TokenTimeoutInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author longxiaonan@aliyun.com
 * @date 2018-11-08
 */
//@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${custom.interceptor.jwt.exclude.path}")
    private String[] jwtExcludePaths;

    @Value("${custom.interceptor.token-timeout.exclude.path}")
    private String[] tokenTimeoutExcludePaths;

    @Value("${custom.interceptor.permission.exclude.path}")
    private String[] permissionExcludePaths;

    /**
     * jwt token验证拦截器
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    /**
     * 权限拦截器
     * @return
     */
//    @Bean
    public PermissionInterceptor permissionInterceptor(){
        return new PermissionInterceptor();
    }

    /**
     * TOKEN超时拦截器
     * @return
     */
//    @Bean
    public TokenTimeoutInterceptor tokenTimeoutInterceptor(){
        return new TokenTimeoutInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("PermissionInterceptor excludePaths : {}", Arrays.toString(permissionExcludePaths));
        // JWT 拦截器
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(jwtExcludePaths);

        // 1.TOKEN超时拦截器
//        registry.addInterceptor(tokenTimeoutInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(tokenTimeoutExcludePaths);
//
//        // 2.权限拦截器
//        registry.addInterceptor(permissionInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(permissionExcludePaths)
//                .excludePathPatterns(operationPlatformIncludePaths);

    }

}
