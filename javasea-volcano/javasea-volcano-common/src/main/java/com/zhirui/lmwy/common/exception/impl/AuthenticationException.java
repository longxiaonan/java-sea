package com.zhirui.lmwy.common.exception.impl;

/**
 * @Description 认证异常和token异常类
 * @Author longxn
 * @Date 21:54 2018/7/3
 **/
public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String msg){
        super(msg);
    }
}
