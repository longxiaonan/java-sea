package com.javasea.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/28 0028 23:58
 */
@RestController
public class DemoController {
    //获取认证信息方法三：只返回存入的user
    @GetMapping("/me3")
    public Object getCurrentUser3(@AuthenticationPrincipal UserDetails user){
        return user;
    }

    //获取认证信息方法二：只返回存入的user
    @GetMapping("/me2")
    public Object getCurrentUser2(Authentication authentication){
        return authentication;
    }

    //获取认证信息方法一：全的认证信息
//    @GetMapping("/me1")
//    public Object getCurrentUser1() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

}
