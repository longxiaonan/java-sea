package com.javasea.orm.rw.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 注释：
 *
 * @author 陈导
 * @date 2020/8/3 14:54
 */
@Aspect
@Component
@Lazy(false)
@Order(0)// order设定AOP指定顺序，使之在数据库事务上先执行
public class SwitchDataSourceAOP {

    Logger log = LoggerFactory.getLogger(SwitchDataSourceAOP.class);

    /** 这里切换到你的方法目录 */
    @Before("execution(* com.javasea.orm.rw..*Service.*(..))")
    public void process(JoinPoint joinPoint){
        System.out.println("SwitchDataSourceAOP ...>>>>>>>>>....");
        String methodName = joinPoint.getSignature().getName();
        if(methodName.startsWith("get") || methodName.startsWith("count") || methodName.startsWith("find")
            || methodName.startsWith("list") || methodName.startsWith("select")){
            log.info("______________selectDataSource");
            DataSourceContextHolder.setDbType("selectDataSource");
        }else{
            //切换dataSource
            log.info("______________updateDataSource");
            DataSourceContextHolder.setDbType("updateDataSource");
        }

    }

}
