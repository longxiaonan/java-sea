package com.iee.orm.mybatisplus.commservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iee.orm.mybatisplus.commservice.UserService;
import com.iee.orm.mybatisplus.entity.User;
import com.iee.orm.mybatisplus.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/3 0003 0:32
 */
@Service
public class CommonUserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
