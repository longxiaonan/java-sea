package com.zhirui.lmwy.wms.demo.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhirui.lmwy.wms.demo.web.entity.User;
import com.zhirui.lmwy.wms.demo.web.mapper.UserMapper;
import com.zhirui.lmwy.wms.demo.web.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/3 0003 0:32
 */
@Service
public class CommonUserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
