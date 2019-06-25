package com.iee.rbac.security.service;

import com.iee.rbac.security.entity.*;
import com.iee.rbac.security.mapper.RoleMapper;
import com.iee.rbac.security.mapper.UserMapper;
import com.iee.rbac.security.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @www.codesheep.cn
 * 20190312
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = this.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Set<String> roleByUser = this.findRoleByUser(user);

        roleByUser.forEach(a->{
            authorities.add(new SimpleGrantedAuthority(a));
        });

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        return user1;
    }

    private Set<String> findRoleByUser(User user) {
        Set<String> resultSet = new HashSet<>();
        UserRoleExample example = new UserRoleExample();
        example.or().andUidEqualTo(user.getUid());
        List<UserRole> userRoles = userRoleMapper.selectByExample(example);
        for(UserRole userRole : userRoles){
            Role role = roleMapper.selectByPrimaryKey(userRole.getRid());
            resultSet.add(role.getRname());
        }
        return resultSet;
    }

    public User findByUsername(String s) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(s);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() > 0){
            return users.get(0);
        }
        return null;
    }

    public User save(User userToAdd) {
        Random rd = new Random();
        userToAdd.setUid(rd.nextInt(10000));
        userMapper.insertSelective(userToAdd);
        return userToAdd;
    }
}
