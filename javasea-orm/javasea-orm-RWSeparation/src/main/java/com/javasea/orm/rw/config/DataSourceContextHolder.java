package com.javasea.orm.rw.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 注释：
 *
 * @author 陈导
 * @date 2020/8/3 14:37
 */
@Component
@Lazy(false)
public class DataSourceContextHolder {


    //采用ThreadLocal保存本地多数据源
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    //设置数据源类型
    public static void setDbType(String dbType){
        contextHolder.set(dbType);
    }

    public static String getDbType(){
        return contextHolder.get();
    }

    public static void clearDbType(){
        contextHolder.remove();
    }

}
