package com.javasea.orm.rw.mapper;

import com.javasea.orm.rw.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 注释：
 *
 * @author 姓名
 * @date 2020/8/3 14:30
 */
@Mapper
public interface UserMapper {

    List<UserEntity> findUser();

    int insertUser(@Param("id") int id);

}
