package com.iee.rbac.shiro.service.impl;

import com.iee.rbac.shiro.entity.User;
import com.iee.rbac.shiro.entity.UserExample;
import com.iee.rbac.shiro.mapper.UserMapper;
import com.iee.rbac.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 22:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Optional<User> findUserByUserName(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() > 0){
            return Optional.ofNullable(users.get(0));
        }
        return Optional.empty();
    }
}
