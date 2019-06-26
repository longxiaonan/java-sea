package com.iee.orm.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iee.orm.mybatisplus.common.PageInfo;
import com.iee.orm.mybatisplus.entity.User;
import com.iee.orm.mybatisplus.mapper.UserMapper;
import com.iee.orm.mybatisplus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

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
        return baseMapper.deleteById( user.getUid() );
    }

    //查
    @Override
    public List<User> selectAll() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    // 查: 普通查
    @Override
    public User findUserByName( String userName ) {
        return userMapper.findUserByName( userName );
    }

    // 查：分页查
    @Override
    public IPage getUserPage(PageInfo pageInfo, User user) {
        Page page = new Page();
        page.setCurrent(pageInfo.getPageIndex());
        page.setSize(pageInfo.getPageSize());
        return userMapper.selectPage( page, null);
    }


}
