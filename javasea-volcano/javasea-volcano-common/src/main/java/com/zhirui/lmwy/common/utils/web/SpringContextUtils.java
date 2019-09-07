package com.zhirui.lmwy.common.utils.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description spring context工具类, 用于在普通类中获取spring中的bean
 * 需要加上@Component, 且被主类扫描到. 否则需要在主类引入该类的bean
 * @Author longxn
 * @Date 16:24 2018/7/3
 **/
@Component
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {

        return getApplicationContext().getBean(clazz);
    }

    /** getBean的测试方法，我们希望通过一个方式，在getApplicationContext().getBean(clazz);获取到的bean为null时，进行延时加载。
     * 通过该方法进行测试，发现SpringContextUtils.applicationContext还会是一直是null，无法达到期望的方式。
     * SpringContextUtils.applicationContext 为null，因为SpringContextUtils是给component，没优先被spring加载 */
    public static <T> T getBean2(Class<T> clazz) {
        T t = null;
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(2000L);
                if (SpringContextUtils.applicationContext == null){
                    Thread.yield();
                    continue;
                }
                t = getApplicationContext().getBean(clazz);
                if (t == null) {
                    Thread.yield();
                    continue;
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
