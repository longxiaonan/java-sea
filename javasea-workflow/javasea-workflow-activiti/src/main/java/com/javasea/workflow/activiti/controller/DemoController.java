package com.javasea.workflow.activiti.controller;

import com.javasea.workflow.activiti.config.ActivitiConfig;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * @ClassName DemoController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/7/12 0012 15:45
 */
@RestController
public class DemoController {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    //测试ProcessEngineConfiguration是否创建成功
    @GetMapping("/test")
    public void test(){
        DataSource dataSource = standaloneProcessEngineConfiguration.getDataSource();
        System.out.println(dataSource);
    }

}
