package com.iee.redis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/9/10 0010 21:03
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
}
