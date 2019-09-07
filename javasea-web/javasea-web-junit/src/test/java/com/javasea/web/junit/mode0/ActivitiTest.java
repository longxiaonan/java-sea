package com.javasea.web.junit.mode0;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @ClassName ActivitiTest
 * @Description 测试Activiti25张表的生成
 * @Author longxiaonan@163.com
 * @Date 2019/7/12 0012 16:13
 */

public class ActivitiTest extends BaseTest {

    @Test
    public void testGenTable(){

    }

    /** 因为不是xml配置文件，所以该方式不能生成表 */
//    @Test
//    public void testGenTable2(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        System.out.println(processEngine);
//    }
}
