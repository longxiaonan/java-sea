package com.iee.orm.mybatis.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 描述：登录用户信息 创建日期: 2018-05-19 10:38
 * @author longxiaonan@aliyun.com
 */
@Component
public class UserHelper implements ApplicationContextAware {
    /**
     * 获取当前用户id
     */
    public static String getCurrentUserId() {
        return "p21";
    }

    /**
     * 获取当前用户的租户id
     */
    public static String getTenantId() {
        return "2";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        init(applicationContext);
    }

    /** 可以通过 applicationContext 初始化一些bean */
    private void init(ApplicationContext applicationContext) {

    }
}
