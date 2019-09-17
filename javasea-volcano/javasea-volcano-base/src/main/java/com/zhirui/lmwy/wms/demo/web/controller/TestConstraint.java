package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.wms.demo.web.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName TestConstraint
 * @Description 自定义 @Phone 和 @Email 注解的测试, 已经定义了这两个注解和处理类，在这里进行测试
 * @Author longxiaonan@163.com
 * @Date 2019/9/10 0010 20:43
 */
@RestController
public class TestConstraint {

    @GetMapping("testConstraint")
    public Student test(@Valid Student student){
        return student;
    }
}
