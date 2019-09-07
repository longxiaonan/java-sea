package com.iee.webserver1.controller;

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
 * @ClassName Web1Controller
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/5 12:03
 */
@RestController
@RequestMapping("/api")
public class Web1Controller {

    StringRedisTemplate redisTemplate;

    @Autowired
    public Web1Controller(StringRedisTemplate redisTemplate ) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/testSpringSession/{name}")
    public void testSpringSession(@PathVariable String name, HttpServletRequest request){
        System.out.println("获取到的姓名为：" + name);
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        requestAttributes.getRequest().setAttribute("name",name);
        request.getSession().setAttribute("name",name);
    }

    @GetMapping("/getAttr")
    public Object testSpringSession2(HttpServletRequest request){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final Object name = request.getSession().getAttribute("name");
        return name;
    }

    @GetMapping("/testSet")
    public void testSet(){
        System.out.println(">>>web111111");
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
