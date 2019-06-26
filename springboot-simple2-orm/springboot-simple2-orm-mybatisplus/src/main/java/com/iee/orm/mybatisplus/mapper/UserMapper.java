package com.iee.orm.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iee.orm.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    // 普通查询
    User findUserByName(String userName);

    // 分页查询, 需要在mapper.xml中手动实现
//    IPage<List<User>> getUsersPage(Page page, @Param("query") User user);

    /** 通过Select Insert Update Delete 注解，可以写增删查改语句 */
    @Select("select * from user where id = #{id}")
    User findUserById(int id);

}
