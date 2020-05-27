package com.javasea.workflow.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * @ClassName GatewayExclusive
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2020/5/25 0025 22:39
 */
public class GatewayExclusive {

    public void deploy(){
        //1.获取processEngine实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.获取repositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday-exclusive.bpmn")//添加bpmn资源
//                .addClasspathResource("diagram/holiday4.png") //添加png资源
                .name("请假流程-流程变量")//部署的名字
                .deploy();//执行部署
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

}
