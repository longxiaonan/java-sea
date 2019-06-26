package com.iee.orm.mybatisplus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iee.orm.mybatisplus.common.PageInfo;
import com.iee.orm.mybatisplus.entity.User;
import com.iee.orm.mybatisplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserContorller {

    @Autowired
    private UserService userService;

    // 查
    @GetMapping( value = "/selectAll")
    public List<User> selectAll() {
        return userService.selectAll();
    }

    // 增
    @PostMapping( value = "/insert")
    public Object insert( @RequestBody User user ) {
        return userService.insertUser( user );
    }

    // 改
    @PostMapping( value = "/update")
    public Object update( @RequestBody User user ) {
        return userService.updateUser( user );
    }

    // 删
    @PostMapping( value = "/delete")
    public Object delete( @RequestBody User user ) {
        return userService.deleteUser( user );
    }

    // 查
    @GetMapping( value = "/getUserByName")
    public Object getUserByName( @RequestParam String username ) {
        return userService.findUserByName( username );
    }

    // 分页
    @GetMapping( value = "/page")
    public List<User> getUserPage(PageInfo pageInfo, User user ) {
        pageInfo.setPageIndex(1);
        pageInfo.setPageSize(3);
        IPage userPage = userService.getUserPage(pageInfo, user);
        List<User> records = userPage.getRecords();
        long total = userPage.getTotal();
        System.out.println("total: "+total);
        System.out.println("records: "+records);
        return records;
    }
}
