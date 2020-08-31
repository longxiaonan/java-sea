package com.iee.orm.mybatisplus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iee.orm.mybatisplus.common.PageInfo;
import com.iee.orm.mybatisplus.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    int insertUser(User user);
    int updateUser(User user);

    // 改
    int updateUserByWrapper(User user);

    // 删
    int deleteById(Long user);

    List<User> selectByMap();

    List<User> selectByWrapperAllEq();

    //条件查询
    List<User> selectByWrapperEntity();

    List<Map<String, Object>> selectByWrapperMaps();

    List<Map<String, Object>> selectByWrapperLambda();

    List<User> selectMy();

    //精确条件查询
    List<User> selectByWrapper();

    User findUserByName(String userName);
    IPage getUserPage(PageInfo page, User user);

    // 删
    int deleteByMap(User user);

    List<User> selectAll();
}
