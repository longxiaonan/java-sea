package com.iee.rabc.shiro.controller;

import com.iee.rabc.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

//    @RequestMapping("/login1")
//    public ModelAndView login(ModelAndView model) {
//        model.setViewName("login");
//        return model;
//    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    @ResponseBody
    public User test() {
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        return principal;
    }

    @RequestMapping("/loginUser")
    public String loginUser(String username, String password, HttpSession session) {
        Objects.requireNonNull(username, "用户名不能为空");
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            User user=(User) subject.getPrincipal();
            session.setAttribute("user", user);
            return "index";
        } catch(Exception e) {
            return "login";//返回登录页面
        }

    }
    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }
}
