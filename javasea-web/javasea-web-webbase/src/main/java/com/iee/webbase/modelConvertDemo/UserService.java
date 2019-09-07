package com.iee.webbase.modelConvertDemo;

import com.iee.webbase.modelConvertDemo.exception.NotFindUserException;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/14 0014 11:33
 */
@Service
public class UserService {
    public User addUser(User user) {
        if(null == user){
            throw new NotFindUserException("用户未找到");
        }
        return user;
    }
}
