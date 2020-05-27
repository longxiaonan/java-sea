package com.iee.orm.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iee.orm.mybatis.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 租户的用户表 Mapper 接口
 * </p>
 *
 * @author longxiaonan
 * @since 2019-11-20
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据ID获取查询对象
     * @param userId
     * @return
     */
    User getUserById(String userId);

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserPageList();


    Integer inserBatch();
}
