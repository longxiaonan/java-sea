package com.iee.orm.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("user")  //默认是实体类名，如果不一致必须指定，括号里面的表名
public class User {

    //ALTER TABLE USER CHANGE COLUMN uid uid BIGINT(20) AUTO_INCREMENT
    //IdType.AUTO 数据库自增， IdType.NONE 雪花算法
    @TableId(type = IdType.AUTO, value = "uid") //如果不一致必须指定，括号里面是列名
    private Long uid;
    @TableField(value = "username") //如果不一致必须指定，括号里面是列名
    private String username;
    private String password;

    private Integer age;

    private String createTime;

    @TableField(exist = false) //数据库没该字段，通过exist = false设置，否则会报错
    private String remark;


}
