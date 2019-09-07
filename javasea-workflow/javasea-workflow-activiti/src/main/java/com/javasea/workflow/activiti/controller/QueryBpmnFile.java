package com.javasea.workflow.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

/**
 * @ClassName QueryBpmnFile
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/21 0021 20:44
 */
@RestController
public class QueryBpmnFile {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    /** 1.第一种方式，使用activiti的api实现
     *  2.第二种方式，使用jdbc对blob类型，clob数据类型的读取，并保存IO流转换，使用common-io可以轻松解决
     */
    @GetMapping("/queryBpmnFile")
    public void queryBpmnFile() throws IOException {
        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到runtimeservice对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.得到ProcessDefinitionQuery对象,可以认为就是一个查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4.设置查询参数，查询出定义的流程，流程定义的部署id， id在act_re_deployment
        List<ProcessDefinition> holidayList = processDefinitionQuery.processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()   //设置排序方式，根据流程定义的版本号进行排序
                .desc()
//                .singleResult()   //如果只有一个结果，
                .list();

        //5.获取流程部署id
        for (ProcessDefinition pd : holidayList) {
            System.out.println("流程定义的部署id："+pd.getDeploymentId());

            //6.读取图片信息和bpmn文件信息（输入流）
            //参数1：部署id，参数2：png图片资源名称
            InputStream pngIs = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getDiagramResourceName());
            //参数1：部署id，参数2：bpmn文件资源名称
            InputStream bpmnIs = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getResourceName());

            //7.构建出OutputStream流
            OutputStream pngOs = new FileOutputStream("C:\\temp\\"+pd.getDiagramResourceName());
            OutputStream bpmnOs = new FileOutputStream("C:\\temp\\"+pd.getResourceName());

            //8.输入输出流转换
            IOUtils.copy(pngIs,pngOs);
            IOUtils.copy(bpmnIs,bpmnOs);

            //9.关闭流
            pngOs.close();
            bpmnOs.close();
            pngIs.close();
            bpmnIs.close();
        }

    }
}
