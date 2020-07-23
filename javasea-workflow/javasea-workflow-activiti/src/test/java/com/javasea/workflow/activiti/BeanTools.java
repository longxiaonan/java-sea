package com.javasea.workflow.activiti;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 */
@Configuration
public class BeanTools implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public  void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public  static <T> T getBean(Class<T> classname) {
            return applicationContext.getBean(classname);
    }

    public static void setApplicationContext1(ApplicationContext context) {
        applicationContext = context;
    }
}
