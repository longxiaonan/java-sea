package com.iee.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Result
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/10 0010 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result2<T> {
    private Integer code;
    private String msg;
    private T data;
}
