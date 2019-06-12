package com.iee.rbac.shiro.service.impl;

import com.iee.rabc.shiro.entity.*;
import com.iee.rbac.shiro.mapper.ModuleMapper;
import com.iee.rbac.shiro.mapper.ModuleRoleMapper;
import com.iee.rbac.shiro.mapper.RoleMapper;
import com.iee.rbac.shiro.mapper.UserRoleMapper;
import com.iee.rbac.shiro.service.RoleService;
import com.iee.rbac.shiro.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    ModuleMapper moduleMapper;

    /** 获取用户下的权限，正式环境中请不要这样写，我这样写是为了不用去写sql !!! */
    @Override
    public Set<String> getModulesString(User user) {
        Set<String> set = new HashSet<>();
        UserRoleExample userExample = new UserRoleExample();
        userExample.or().andUidEqualTo(user.getUid());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userExample);
        userRoles.forEach(userRole -> {
            ModuleRoleExample roleExample = new ModuleRoleExample();
            roleExample.or().andRidEqualTo(userRole.getRid());
            List<ModuleRole> moduleRoles = moduleRoleMapper.selectByExample(roleExample);
            moduleRoles.forEach(moduleRole -> {
                Module module = moduleMapper.selectByPrimaryKey(moduleRole.getMid());
                set.add(module.getMname());
            });
        });
        return set;
    }

    public Set<String> getRolesString(User user) {
        Set<String> set = new HashSet<>();
        UserRoleExample userExample = new UserRoleExample();
        userExample.or().andUidEqualTo(user.getUid());
        List<UserRole> userRoles = userRoleMapper.selectByExample(userExample);
        userRoles.forEach(userRole -> {
            ModuleRoleExample roleExample = new ModuleRoleExample();
            roleExample.or().andRidEqualTo(userRole.getRid());
            List<ModuleRole> moduleRoles = moduleRoleMapper.selectByExample(roleExample);
            moduleRoles.forEach(moduleRole -> {
                Role module = roleMapper.selectByPrimaryKey(moduleRole.getRid());
                set.add(module.getRname());
            });
        });
        return set;
    }
}
