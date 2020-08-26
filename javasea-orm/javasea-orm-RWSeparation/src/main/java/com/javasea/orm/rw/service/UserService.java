package com.javasea.orm.rw.service;

import com.javasea.orm.rw.entity.UserEntity;
import com.javasea.orm.rw.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * 注释：
 *
 * @author 姓名
 * @date 2020/8/3 14:29
 */
@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;

    public List<UserEntity> findUser() {
        return userMapper.findUser();
    }


    public int insertUser(Integer id) {
        return userMapper.insertUser(id);
    }

}
