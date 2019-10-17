package com.iee.rbac.shiro.system.mapper;

import com.iee.rbac.shiro.system.entity.UserRole;
import com.iee.rbac.shiro.system.entity.UserRoleExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    int countByExample(UserRoleExample example);

    int deleteByExample(UserRoleExample example);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);

    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);
}
