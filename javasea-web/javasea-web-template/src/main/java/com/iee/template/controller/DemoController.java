package com.iee.template.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName DemoController
 * @Description 由于要返回模板页面文件，所以我们只能使用@modelConvertDemo 而不可以使用@RestController
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 9:18
 */
@Controller
public class DemoController {

    @GetMapping("/thymeLeaf")
    public ModelAndView hello(ModelAndView model){
        // templates下的文件名
        model.setViewName("hello");
        // 添加内容
        model.addObject("name", "ThymeLeaf");
        return model;
    }

    @GetMapping("/freemarker")
    public String helloFreeMarker(ModelMap model){
        // 添加内容
        model.addAttribute("name", "FreeMarker");
        return "word";
    }

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("content", "Freemarker");
        return "index";
    }
}
