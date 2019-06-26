package com.iee.orm.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/** 在实体类与数据库表明不是对应的情况下使用 e.g.
 @Data
 @TableName(value = "user")
 public class UserEntity {
 private Long id;
 private String name;

 @TableField(value = "age")
 private Integer userAge;
 private String email;
 }
 **/
@Data
@TableName("user")  //
public class User {
    @TableId(value = "uid")
    private Long uid;
    private String username;
    private String password;
}
