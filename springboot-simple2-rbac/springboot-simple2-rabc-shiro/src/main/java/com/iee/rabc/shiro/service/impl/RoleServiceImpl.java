package com.iee.rabc.shiro.service.impl;

import com.iee.rabc.shiro.entity.*;
import com.iee.rabc.shiro.mapper.ModuleRoleMapper;
import com.iee.rabc.shiro.mapper.RoleMapper;
import com.iee.rabc.shiro.mapper.UserRoleMapper;
import com.iee.rabc.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 22:16
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    ModuleRoleMapper moduleRoleMapper;
    @Autowired
    RoleMapper roleMapper;

    /** 获取用户下的权限，正式环境中请不要这样写，我这样写是为了不用去写sql !!! */
    @Override
    public List<String> getModules(User user) {
        List<String> list = new ArrayList<>();
        UserRoleExample userExample = new UserRoleExample();
        userExample.or().andUidEqualTo(user.getUid());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userExample);
        userRoles.forEach(userRole -> {
            ModuleRoleExample roleExample = new ModuleRoleExample();
            List<ModuleRole> moduleRoles = moduleRoleMapper.selectByExample(roleExample);
            moduleRoles.forEach(moduleRole -> {
                Role role = roleMapper.selectByPrimaryKey(moduleRole.getRid());
                list.add(role.getRname());
            });
        });

        return list;
    }
}
