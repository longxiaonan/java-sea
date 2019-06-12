package com.iee.rabc.shiro.common;

import lombok.Data;

/**
 * @ClassName ResultModel
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 23:35
 */
@Data
public class ResultModel {
    private  int code;
    private String message;
    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
