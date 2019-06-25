package com.iee.rbac.security.service;


import com.iee.rbac.security.entity.User;

/**
 * @www.codesheep.cn
 * 20190312
 */
public interface AuthService {

    User register(User userToAdd);
    String login(String username, String password);
}
