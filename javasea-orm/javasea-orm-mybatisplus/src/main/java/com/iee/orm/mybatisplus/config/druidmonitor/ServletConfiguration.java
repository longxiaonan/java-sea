package com.iee.orm.mybatisplus.config.druidmonitor;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description druid监控配置，按照需求配置, 访问地址为：http://XXXX:8080/druid
 * @Author longxiaonan@163.com
 * @Date 0:32 2019/9/9 0009
 **/
@Configuration
public class ServletConfiguration {
    @Bean
    public ServletRegistrationBean druidStatViewServletBean() {
        //后台的路径
        ServletRegistrationBean statViewServletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> params = new HashMap<>();
        //账号密码，是否允许重置数据
        params.put("loginUsername","admin");
        params.put("loginPassword","admin");
        params.put("resetEnable","true");
        statViewServletRegistrationBean.setInitParameters(params);
        return statViewServletRegistrationBean;
    }
}
