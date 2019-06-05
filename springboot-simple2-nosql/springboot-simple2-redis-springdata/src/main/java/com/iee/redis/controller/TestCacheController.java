package com.iee.redis.controller;

import com.iee.redis.entity.Person;
import com.iee.redis.service.TestCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestCacheController
 * @Description 通过cachaing的注解可以方便的对redis进行读取
 * @Author longxiaonan@163.com
 * @Date 2018/9/13 0013 13:20
 */
@RestController
public class TestCacheController {

    @Autowired
    TestCacheService testCacheService;

    /** 第一次访问该方法会执行service方法体中的方法, redis中出现了一个key为thisredis::users_1的数据,值看不到
     * 第二次访问该方法不会执行service方法体中的方法,而是直接从缓存中获取
     * 第四次访问该方法, 因为第三次执行的是删除缓存, 所以会执行service方法体中的方法
     * @param id
     */
    @GetMapping("/111")
    public Person t111(Integer id){
        return testCacheService.findUser(id);
    }

    /** 第三次访问该方法删除缓存
     * @param id
     */
    @GetMapping("/testCacheDel")
    public void testCacheDel(Integer id){
        testCacheService.delUser(id);
    }
}
