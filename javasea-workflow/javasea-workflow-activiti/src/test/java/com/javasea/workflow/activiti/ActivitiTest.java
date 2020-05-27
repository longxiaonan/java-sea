package com.javasea.workflow.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ActivitiTest
 * @Description 测试Activiti25张表的生成
 * @Author longxiaonan@163.com
 * @Date 2019/7/12 0012 16:13
 */

public class ActivitiTest extends BaseTest {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    /** 生成表：生成25张表
     * DROP DATABASE `activiti_lxn`;
     * CREATE DATABASE `activiti_lxn`;
     * */
    @Test
    public void testGenTable(){
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

    /** 因为不是xml配置文件，所以该方式不能生成表 */
//    @Test
//    public void testGenTable2(){
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        System.out.println(processEngine);
//    }
}
