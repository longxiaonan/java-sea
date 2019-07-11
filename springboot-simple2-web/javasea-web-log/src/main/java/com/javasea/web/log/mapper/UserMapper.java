package com.javasea.web.log.mapper;

import com.javasea.web.log.entity.SysUser;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Author longxiaonan@163.com
 * @Date 2018/8/8 16:55
 */
//@Repository
public interface UserMapper {
    //分页查询用户列表
    List<SysUser>  queryUserAll();
}
