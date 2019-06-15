package com.iee.orm.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iee.orm.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    // 普通查询
    User getUserByName(String userName);

    // 分页查询
    IPage<List<User>> getUsersPage(Page page, @Param("query") User user);
}
