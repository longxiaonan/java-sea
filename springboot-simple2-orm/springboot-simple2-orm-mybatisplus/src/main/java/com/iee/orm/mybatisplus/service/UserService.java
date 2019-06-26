package com.iee.orm.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iee.orm.mybatisplus.common.PageInfo;
import com.iee.orm.mybatisplus.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(User user);
    User findUserByName(String userName);
    IPage getUserPage(PageInfo page, User user);

    List<User> selectAll();
}
