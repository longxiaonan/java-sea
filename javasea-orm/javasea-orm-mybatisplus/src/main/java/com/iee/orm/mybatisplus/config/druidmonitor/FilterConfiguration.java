package com.iee.orm.mybatisplus.config.druidmonitor;



import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description druid文件过滤器，按照需求配置
 * @Author longxiaonan@163.com
 * @Date 0:32 2019/9/9 0009
 **/
@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean druidStatFilterBean() {
        FilterRegistrationBean druidStatFilterBean=new FilterRegistrationBean(new WebStatFilter());
        List<String> urlPattern=new ArrayList<>();
        urlPattern.add("/*");
        druidStatFilterBean.setUrlPatterns(urlPattern);
        Map<String,String> initParams=new HashMap<>();
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        druidStatFilterBean.setInitParameters(initParams);
        return druidStatFilterBean;
    }
}
