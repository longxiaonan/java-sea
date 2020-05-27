package com.iee.orm.mybatis.service;

import com.iee.orm.mybatis.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 租户的用户表 服务类
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
public interface UserService extends IService<User> {

    User getUserById(String userId);

    List<User> getUserPageList();

    Integer inserBatch();
}
