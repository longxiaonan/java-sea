package com.javasea.orm.rw.controller;

import com.javasea.orm.rw.entity.UserEntity;
import com.javasea.orm.rw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/**
 * 注释：
 *
 * @author 姓名
 * @date 2020/8/3 14:30
 */
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "selectDataSource")
    private DataSource selectDataSource;

    @Autowired
    @Qualifier(value = "updateDataSource")
    private DataSource updateDataSource;

    @RequestMapping("/test")
    public void test(){
        System.out.println(selectDataSource);
        System.out.println(updateDataSource);
    }

    @RequestMapping("/findUser")
    public List<UserEntity> findUser(){
        return userService.findUser();
    }


    @RequestMapping("/insertUser")
    public int insertUser(Integer id){
        return userService.insertUser(id);
    }




}
