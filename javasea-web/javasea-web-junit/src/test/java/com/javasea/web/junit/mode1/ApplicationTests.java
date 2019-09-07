package com.javasea.web.junit.mode1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName ApplicationTests
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/27 0027 22:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Test
    public void test(){
        System.out.println("hello world");
        //简单验证结果集是否正确
        Assert.assertEquals(3, 4);
        //验证结果集，提示
        Assert.assertTrue("错误，正确的返回值为200", 200 == 200);
        Assert.assertFalse("错误，正确的返回值为200", 200 != 200);
    }
}
