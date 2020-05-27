package com.iee.orm.mybatis.service.impl;

import com.iee.orm.mybatis.entity.User;
import com.iee.orm.mybatis.mapper.UserMapper;
import com.iee.orm.mybatis.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 租户的用户表 服务实现类
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public List<User> getUserPageList() {
        return userMapper.getUserPageList();
    }


    @Override
    public Integer inserBatch(){
        Integer integer = userMapper.inserBatch();
        return integer;
    }

}
