package com.iee.common.entity;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

/** 为了测试 BeanCopy，UserDto 比 User类 多了个String addr; mobile 类型 变成了 int */

@Data
@Builder
public class UserDto {

    public UserDto(Long uid, String username, int age) {
        this.uid = uid;
        this.username = username;
        this.age = age;
    }

    public UserDto(Long uid, String username, String password, String sex, Integer age,String createTime, String remark, int mobile,
                   String email, String addr) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.createTime = createTime;
        this.remark = remark;
        this.mobile = mobile;
        this.email = email;
        this.addr = addr;
    }

    private Long uid;

    private String username;

    private String password;

    private String sex;

    private Integer age;

    private String createTime;

    private String remark;

    private int mobile;

    private String email;

    private String addr;

    public UserDto() {

    }
}
