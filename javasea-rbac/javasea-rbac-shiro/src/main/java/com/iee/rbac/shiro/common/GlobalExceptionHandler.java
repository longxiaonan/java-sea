package com.iee.rbac.shiro.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常类
 */
//@ControllerAdvice("com.iee.rabc.shiro")
//@Order(-1)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 其他异常抛出信息
     *
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultModel otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        log.error(ex.getMessage(), ex);
        return new ResultModel(500, ex.getMessage());
    }


    /**
     * 账号被冻结异常
     */
    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String accountLocked(DisabledAccountException e, Model model) {
        model.addAttribute("message", "账号被冻结");
        return "/login.html";
    }

    /**
     * 账号密码错误异常
     */
    @ExceptionHandler(CredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String credentials(CredentialsException e, Model model) {
        model.addAttribute("message", "账号密码错误");
        return "/login.html";
    }
}
