package com.iee.file.upload.controller;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;

/**
 * spring 获取注解工具类
 * */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * spring容器添加bean注解
     * */
    public static void addBean(String name, Class<T> clazz){
        ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        beanFactory.registerBeanDefinition(name, beanDefinitionBuilder.getBeanDefinition());
    }


    /**
     * 获取对象 这里重写了bean方法，起主要作用
     */
    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }

    /**
     * @Description 通过类名获取bean
     * @Author longxiaonan@163.com
     * @Date 11:42 2019/5/30 0030
     **/
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static Object getTarget(Object proxy) throws Exception {
        if(!AopUtils.isAopProxy(proxy)) {
            return proxy;//不是代理对象
        }
        if(AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else { //cglib
            return getCglibProxyTargetObject(proxy);
        }
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        return target;
    }


    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
        return target;
    }

    public static Object  getAutoWriedBeanName(String beanName){

        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return wac.getBean(beanName);
    }
}


