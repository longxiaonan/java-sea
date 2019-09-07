package com.zhirui.lmwy.common.exception.impl;

/**
 * @Description 自定义业务异常类, 在业务逻辑异常的时候进行抛出
 * @Author longxn
 * @Date 2018/7/3 21:50
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String msg){
        super(msg);
    }
}
