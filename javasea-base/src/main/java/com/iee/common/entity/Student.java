package com.iee.common.entity;

import lombok.Data;

/**
 * @ClassName Student
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/5 0005 23:44
 */
@Data
public class Student {
    public Student(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private Integer id;
    private String name;
    private String password;
    private Short number;
    private Integer age;
    private String sex;
    public Student() {

    }
}
