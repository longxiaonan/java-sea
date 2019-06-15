package com.iee.orm.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iee.orm.mybatisplus.entity.User;
import com.iee.orm.mybatisplus.mapper.UserMapper;
import com.iee.orm.mybatisplus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 增
    @Override
    public int insertUser(User user) {
        return baseMapper.insert( user );
    }

    // 改
    @Override
    public int updateUser(User user) {
        return baseMapper.updateById( user );
    }

    // 删
    @Override
    public int deleteUser(User user) {
        return baseMapper.deleteById( user.getUserId() );
    }

    // 查: 普通查
    @Override
    public User findUserByName( String userName ) {
        return baseMapper.getUserByName( userName );
    }

    // 查：分页查
    @Override
    public IPage getUserPage(Page page, User user) {
        return baseMapper.getUsersPage( page, user );
    }
}
