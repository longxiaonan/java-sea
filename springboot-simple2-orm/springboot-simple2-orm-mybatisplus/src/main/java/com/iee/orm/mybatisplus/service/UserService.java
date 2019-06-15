package com.iee.orm.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iee.orm.mybatisplus.entity.User;

public interface UserService extends IService<User> {
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(User user);
    User findUserByName(String userName);
    IPage getUserPage(Page page, User user);
}
