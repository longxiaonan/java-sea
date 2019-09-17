
package com.zhirui.lmwy.wms.security.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.zhirui.lmwy.common.persistence.model.result.ResultCodeEnum;
import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import com.zhirui.lmwy.common.web.controller.BaseController;
import com.zhirui.lmwy.wms.security.param.LoginParam;
import com.zhirui.lmwy.wms.security.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 * @auth longxiaonan@aliyun.com
 * @date 2019-05-15
 **/
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ResultModel login(@Validated @RequestBody LoginParam loginParam) throws Exception{
        return loginService.login(loginParam);
    }

    @GetMapping("/non")
    public ResultModel nonLogin() throws Exception{
        System.out.println("nonLogin....");
        return ResultModel.error(ResultCodeEnum.AUTHENTICATION_ERROR);
    }
}
