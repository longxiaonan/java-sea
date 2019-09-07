package com.iee.rbac.shiro.shiro.config;

import com.iee.rbac.shiro.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 集成Shiro标签
 */
@Component
public class ShiroTagFreeMarkerConfigurer implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Autowired
    private FreeMarkerViewResolver resolver;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加上这句后，可以在页面上使用shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
        // 加上这句后，可以在页面上用${context.contextPath}获取contextPath
        resolver.setRequestContextAttribute("context");
    }

}
