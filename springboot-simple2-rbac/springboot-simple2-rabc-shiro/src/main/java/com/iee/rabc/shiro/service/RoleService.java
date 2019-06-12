package com.iee.rabc.shiro.service;

import com.iee.rabc.shiro.entity.User;

import java.util.Set;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/11 0011 22:16
 */
public interface RoleService {
    /** 通过用户获取对应的权限 */
    public Set<String> getModulesString(User user);

    /** 通过用户获取对应的角色 */
    public Set<String> getRolesString(User user);
}
