package com.iee.patterns.proxy.common;

public class UserServiceImpl implements UserService{
    @Override
    public void addUser() {
        System.out.println("添加一个用户");
    }
    @Override
    public void updateUser() {
        System.out.println("更新一个用户");
    }
}
