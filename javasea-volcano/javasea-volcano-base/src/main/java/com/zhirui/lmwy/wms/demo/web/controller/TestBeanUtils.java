package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.utils.BeanCopyUtil;
import com.zhirui.lmwy.wms.demo.web.entity.BeanA;
import com.zhirui.lmwy.wms.demo.web.entity.BeanB;
import com.zhirui.lmwy.wms.demo.web.entity.User;
import com.zhirui.lmwy.wms.demo.web.entity.UserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TestBeanUtils
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2018/11/1 0001 15:44
 */
public class TestBeanUtils {
    public static void main(String[] args) {
        springTest1();
    }
    //优先用BeanCopyUtil, 如果满足不了再用spring的BeanUtils
    public static void springTest1() {
        BeanA ba = new BeanA();
        BeanB bb = new BeanB();
        ba.setName1("ba-name1");
        ba.setName2("ba-name2");
        ba.setBirthday(new Date()); //不可以Date转String
//        ba.setBirthday("2018-11-1");//不能字符串日期转Date类型;
        //不用try catch更加清爽, 第三个参数是ignore的属性, 可以通过扩展忽略点指定属性名的值
        //org.apache.commons.beanutils.BeanUtils 可能会出奇怪的问题，不推荐用
        org.springframework.beans.BeanUtils.copyProperties(ba, bb, "name2");
        System.out.println("------------111springTest1-------ba-------");
        System.out.println(ba);
        System.out.println("------------111springTest1-------bb-------");
        System.out.println(bb);

        User user = new User(1L, "long", 18);
        user.setMobile("137");
        UserDto user1 = new UserDto(2L, "xiao", 19);
        user1.setSex("男");
        user1.setAddr("广东");
        //过滤掉user1中为空的属性
//        BeanCopyUtil.mapperObjectIgnoreNull(user,user1);
        List<User> users = new ArrayList<>();
        users.add(user);
        BeanCopyUtil.mapperObject(user, user1);
        System.out.println(user);
        System.out.println(user1);
    }

}
