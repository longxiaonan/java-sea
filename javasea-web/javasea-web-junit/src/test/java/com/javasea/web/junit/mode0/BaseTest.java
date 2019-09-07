package com.javasea.web.junit.mode0;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ClassName BaseTest
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/12 0012 16:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是web项目，Junit需要模拟ServletContext
@WebAppConfiguration
public class BaseTest {

    @Before
    public void before(){
        System.out.println("开始测试---------------------");
    }

    @After
    public void after(){
        System.out.println("测试结束---------------------");
    }
}
