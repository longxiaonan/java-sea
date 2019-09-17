package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.aop.LogAop;
import com.zhirui.lmwy.common.exception.Assert;
import com.zhirui.lmwy.common.exception.Exceptions;
import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import com.zhirui.lmwy.wms.demo.web.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestAop
 * @Description {@link LogAop} 切面类的测试, 在请求成功
 * @Author longxiaonan@163.com
 * @Date 2019/9/10 0010 21:48
 */
@RestController
@Slf4j
public class TestAop {
    @GetMapping("testSuccess")
    public ResultModel testSuccess(){
        return ResultModel.ok(new User());
    }

    @GetMapping("testFail")
    public ResultModel testFail(){
        return ResultModel.error(new User());
    }

    @GetMapping("testException")
    public ResultModel testException(){
        Assert.fail("testException");
        return null;
    }

}
