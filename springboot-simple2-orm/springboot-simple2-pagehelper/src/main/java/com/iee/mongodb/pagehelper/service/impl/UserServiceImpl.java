package com.iee.mongodb.pagehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iee.mongodb.pagehelper.entity.SysUser;
import com.iee.mongodb.pagehelper.mapper.SysUserMapper;
import com.iee.mongodb.pagehelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description 通过分页插件分页查询用户列表
 * @Author longxiaonan@163.com
 * @Date 2018/8/8 16:24
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper userMapper;

    /** 通过分页插件分页查询用户列表 */
    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.SUPPORTS)
    public void findUserListByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers = userMapper.queryUserAll();
        // 查看到sql中下个查询条件会自动进行分页
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getPageNum());
        System.out.println(pageInfo.getPageSize());
    }

    @Override
    public void findUserListByPage2(Integer pageNum, Integer pageSize) {

    }

}
