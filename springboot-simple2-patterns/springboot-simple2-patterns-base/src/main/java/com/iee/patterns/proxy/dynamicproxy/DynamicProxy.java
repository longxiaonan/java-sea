package com.iee.patterns.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {

    private Object target;//目标对象

    public Object bind(Object target) {
        this.target=target;
        /**
         ClassLoader loader,:指定当前目标对象使用类加载器
         Class<?>[] interfaces,:代理类需要实现的接口列表
         InvocationHandler h:调用处理程序,将目标对象的方法分派到该调用处理程序
         */
        //bind方法简单封装jdk的newProxyInstance()，并返回目标接口对象。invoke方法负责增强目标对象的方法，实现扩展功能。
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception{
        //执行目标对象的方法
        Object result = method.invoke(target,args);
        //实现扩展功能
        System.out.println("打印一下日志");
        return result;
    }
}
