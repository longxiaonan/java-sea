package com.iee.webserver2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @ClassName Web2Controller
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/5 12:04
 */
@RestController
@RequestMapping("/api")
public class Web2Controller {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/testSpringSession")
    public void getTestSpringSession(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String name = (String)request.getSession().getAttribute("name");
        System.out.println(name);
    }

    @GetMapping("/testSet")
    public void testSet(){
        System.out.println(">>>web222222");
        SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();
        stringStringSetOperations.add("aa","bb");
    }

    @GetMapping("/testGet")
    public void testredis(){
        SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();
        Set<String> aa = stringStringSetOperations.members("aa");
        System.out.println(aa);
    }

}
