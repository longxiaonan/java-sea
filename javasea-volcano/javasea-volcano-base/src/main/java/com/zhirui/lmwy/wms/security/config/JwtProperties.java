
package com.zhirui.lmwy.wms.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Jwt配置属性
 * </p>
 * @auth longxiaonan@aliyun.com
 * @date 2019-05-22
 **/
@Data
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {

    /**
     * 密码
     */
    private String secret;

    /**
     * 签发人
     */
    private String issuer;

    /**
     * 主题
     */
    private String subject;

    /**
     * 签发的目标
     */
    private String audience;

    /**
     * token失效时间,默认30分钟
     */
    private Integer expireMinutes;

}
