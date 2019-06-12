package com.iee.rabc.shiro.service;

import com.iee.rabc.shiro.entity.User;

import java.util.Optional;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 22:01
 */
public interface UserService {

    Optional<User> findUserByUserName(String username);
}
