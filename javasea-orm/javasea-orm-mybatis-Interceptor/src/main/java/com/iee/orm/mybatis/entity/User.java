package com.iee.orm.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 租户的用户表
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

/**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private String userId;

/**
     * 租户id
     */
private String tenantId;

/**
     * 姓名
     */
private String name;

/**
     * 邮件
     */
private String email;

/**
     * 手机号
     */
private String mobile;

/**
     * 办公电话
     */
private String telephone;

/**
     * 性别（0：女，1：男 9未知）
     */
private Integer sex;

}
