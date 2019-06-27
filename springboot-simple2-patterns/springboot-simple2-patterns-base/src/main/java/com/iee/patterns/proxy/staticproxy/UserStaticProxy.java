package com.iee.patterns.proxy.staticproxy;

import com.iee.patterns.proxy.common.UserService;

public class UserStaticProxy {

    private UserService userService;

    public UserStaticProxy(UserService userService) {
        this.userService=userService;
    }
    public void addUser() {
        userService.addUser();
        System.out.println("打印一条日志");
    }
}
