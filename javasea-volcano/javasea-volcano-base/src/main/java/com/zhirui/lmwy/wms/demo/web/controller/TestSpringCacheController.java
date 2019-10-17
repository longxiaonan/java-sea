package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.redis.RedisUtils;
import com.zhirui.lmwy.wms.demo.web.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName TestSpringCache
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/11 0011 16:48
 */
@RestController
public class TestSpringCacheController {

    @Autowired
    TestSpringCacheService testSpringCacheService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("testRedis")
    public void testRedis(){

        Student student = new Student();
        student.setId(1);
        student.setName("longxiaonan");
        student.setBirth(new Date());
        redisUtils.set("testRedis", student);

        Object testRedis = redisUtils.get("testRedis");
        System.out.println(testRedis);
    }

    @GetMapping("testSpringCache1/{id}")
    public Student test1(@PathVariable Integer id){
        return testSpringCacheService.findStudent11(id);
    }

    @GetMapping("testSpringCache1List/{id}")
    public void test11(@PathVariable Integer id){
        testSpringCacheService.findStudent11List(id);
    }

    @GetMapping("testSpringCache2/{id}")
    public void test2(@PathVariable Integer id){
        testSpringCacheService.delStudent12(id);
    }

    @GetMapping("testSpringCache3/{id}")
    public Student test3(@PathVariable Integer id){
        return testSpringCacheService.findStudent21(id);
    }

    @GetMapping("testcachename")
    public Student testcachename(@PathVariable Integer id){
        return testSpringCacheService.testcachename(id);
    }

    @GetMapping("testSpringCache4")
    public void test4(@PathVariable Integer id){
        testSpringCacheService.delStudent22(id);
    }


}
