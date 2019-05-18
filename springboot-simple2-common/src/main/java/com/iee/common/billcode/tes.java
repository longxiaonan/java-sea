package com.iee.common.billcode;

import com.iee.common.entity.User;

import java.util.Optional;

/**
 * @ClassName tes
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/20 0020 11:31
 */
public class tes {
    public static void main(String[] args) {
        User user = new User();
        Integer integer = Optional.ofNullable(user).map(a -> a.getAge()).orElseThrow(() -> new RuntimeException());
        
    }
}
