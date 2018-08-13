package com.iee.pagehelper.service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/8/8 16:23
 */
public interface UserService {

    void findUserListByPage(Integer pageNum, Integer pageSize);

    void findUserListByPage2(Integer pageNum, Integer pageSize);
}
