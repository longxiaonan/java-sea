package com.zhirui.lmwy.wms.demo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhirui.lmwy.wms.demo.web.entity.User;

public interface UserMapper extends BaseMapper<User> {

    // 普通查询
//    User findUserByName(String userName);
//
//    // 分页查询, 需要在mapper.xml中手动实现
////    IPage<List<User>> getUsersPage(Page page, @Param("query") User user);
//
//    /** 通过Select Insert Update Delete 注解，可以写增删查改语句 */
//    @Select("select * from user where id = #{id}")
//    User findUserById(int id);
//
//    @Select("select * from user ${ew.customSqlSegment}")
//    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

}
