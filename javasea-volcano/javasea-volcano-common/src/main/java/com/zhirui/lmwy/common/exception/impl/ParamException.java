package com.zhirui.lmwy.common.exception.impl;

import lombok.Data;

/**
 * @Description 参数异常类
 * @Author longxn
 * @Date 21:54 2018/7/3
 **/
@Data
public class ParamException extends RuntimeException{

    /**
     * @Description 保存检验的参数数据,用于返回给用户
     * @Author
     * @Date 12:12 2018/7/6
     **/
    private Object data;

    public ParamException(String msg){
        super(msg);
    }

    public ParamException(String message, Object data) {
        super(message);
        this.data = data;
    }
}
