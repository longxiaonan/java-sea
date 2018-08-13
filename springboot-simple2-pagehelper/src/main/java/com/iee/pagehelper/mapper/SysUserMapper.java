package com.iee.pagehelper.mapper;

import com.iee.pagehelper.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Author longxiaonan@163.com
 * @Date 2018/8/8 16:55
 */
//@Repository
public interface SysUserMapper {
    //分页查询用户列表
    List<SysUser>  queryUserAll();
}
