package com.zhirui.lmwy.common.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SwaggerProperties
 * @Description 加载swagger的配置文件
 * @Author longxiaonan@163.com
 * @Date 2018/7/13 15:36
 */
@ConfigurationProperties(prefix = "swagger")
//@ConditionalOnExpression("${swagger.open}")
public class SwaggerProperties {
    /**
     protocol: http          //协议，http或https
     base-package: com.vdp   //一定要写对，会在这个路径下扫描controller定义
     title: REST接口定义
     version: 1.0
     description: vdp-cloud项目RESTful API
     **/
    private String protocol;
    private String basePackage;
    private String title;
    private String version;
    private String description;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
