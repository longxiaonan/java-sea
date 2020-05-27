package com.iee.orm.mybatis.controller;

import com.iee.orm.mybatis.entity.User;
import com.iee.orm.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 租户的用户表 前端控制器
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId){
        User user = userService.getUserById(userId);
        return user;
    }

    @GetMapping("/list")
    public List<User> getUserPageList(){
        List<User> userList = userService.getUserPageList();
        return userList;
    }

    @GetMapping("/inserBatch")
    public Integer inserBatch(){
        Integer integer = userService.inserBatch();
        return integer;
    }

}

