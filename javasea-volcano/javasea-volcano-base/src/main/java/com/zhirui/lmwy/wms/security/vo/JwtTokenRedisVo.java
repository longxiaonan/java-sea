
package com.zhirui.lmwy.wms.security.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 * @auth longxiaonan@163.com
 * @date 2019-05-23
 **/
@Data
public class JwtTokenRedisVo implements Serializable {
    private static final long serialVersionUID = 5631646796482815114L;

    /**
     * 登录token
     */
    private String token;

    /**
     * 登录token md5
     */
    private String tokenMd5;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 过期时间
     */
    private Date expiredDate;
}
