package com.iee.orm.mybatisplus.commservice.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.iee.orm.mybatisplus.commservice.UserService;
import com.iee.orm.mybatisplus.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/3 0003 0:34
 */
@RestController
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("getOne")
    public void getOne(){
        //如果大于1条会报错，所有，第三个参数为false
        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 18), false);
    }

    @GetMapping("saveBatch")
    public void saveBatch(){
        //如果大于1条会报错，所有，第三个参数为false
        User user1 = new User();
        user1.setUsername("关羽");
        user1.setAge(28);

        User user2 = new User();
        user2.setUid(3L);//如果设置id用saveOrUpdateBatch，那么会先查询后更新或者插入
        user2.setUsername("张飞");
        user2.setAge(27);

        List<User> users = Arrays.asList(user1, user2);
        this.userService.saveOrUpdateBatch(users);
    }

    @GetMapping("lambdaSelect")
    public List<User> lambdaSelect(){
        Map<SFunction<User, ?>, Object> map = new HashMap<>();
        map.put(User::getUsername, "long");
        userService.lambdaQuery().allEq(map).list();
//        List<User> aLong = userService.lambdaQuery().gt(User::getUsername, "long").like(User::getAge, 22).list();
        return null;
    }

    @GetMapping("lambdaUpdate")
    public boolean lambdaUpdate(){
        boolean aLong = userService.lambdaUpdate().gt(User::getUsername, "long").like(User::getAge, 22).set(User::getAge, 26).update();
        return aLong;
    }

    @GetMapping("lambdaRemove")
    public boolean lambdaRemove(){
        boolean aLong = userService.lambdaUpdate().gt(User::getUsername, "long").like(User::getAge, 22).set(User::getAge, 26).remove();
        return aLong;
    }

}
