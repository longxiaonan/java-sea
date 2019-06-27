package com.iee.patterns.proxy;

import com.iee.patterns.proxy.cglibproxy.CGLibProxy;
import com.iee.patterns.proxy.common.UserService;
import com.iee.patterns.proxy.common.UserServiceImpl;
import com.iee.patterns.proxy.dynamicproxy.DynamicProxy;
import com.iee.patterns.proxy.staticproxy.UserStaticProxy;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/27 0027 20:05
 */
public class Demo {
    public static void main(String[] args) {
//        staticProxy();
//        jdkDynamicProxy();
        cglibproxy();
    }

    private static void cglibproxy() {
        /**上面两种代理方式，目标对象UserServiceimpl都实现了一个接口，如果只是一个普通的类，没有实现任何接口，
         * 该如何进行代理呢？这就引出了CGLib动态代理。CGLib动态代理也叫子类代理，
         * 它是在内存中构建一个子类对象从而实现对目标对象功能的扩展。
           要用cglib代理，需要导入相应的包，好在spring已经集成了它，引入spring即可。
         **/
        CGLibProxy cgLibProxy = new CGLibProxy();
        UserService userService2 = (UserService) cgLibProxy.bind(new UserServiceImpl());
        userService2.addUser();
        userService2.updateUser();
    }

    private static void jdkDynamicProxy() {
        /**
         * 动态代理模式
         */
        DynamicProxy dynamicProxy = new DynamicProxy();
        UserService userService1 = (UserService) dynamicProxy.bind(new UserServiceImpl());
        userService1.addUser();
        userService1.updateUser();
    }

    private static UserService staticProxy() {
        /** 静态代理模式
         * 1、接口增加方法，代理类需要同步维护。
         * 2、接口越多，需要创建的代理类就越多。比如以后我们有TeacherService,StudentService，就需要创建TeacherStaticProxy,StudentStaticProxy，这样就增加了代码量。
         * */
        UserService userService = new UserServiceImpl();
        UserStaticProxy staticProxy = new UserStaticProxy(userService);
        staticProxy.addUser();
        return userService;
    }

}
