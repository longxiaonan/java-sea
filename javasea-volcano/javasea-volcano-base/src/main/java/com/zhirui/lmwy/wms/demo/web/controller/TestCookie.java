package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.utils.web.CookieUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TestCokie
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/7 0007 9:58
 */
@RestController
public class TestCookie {

    @GetMapping("testCookie")
    public void test(HttpServletRequest request, HttpServletResponse response){
        CookieUtils.setCookie(request,response,"cookieName", "cookieContext");
    }
}
