package com.javasea.workflow.activiti.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SuspendProcessInstance
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/21 0021 23:27
 */
@RestController
public class SuspendProcessInstance {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    /** 全部暂停或者启动实例， 当公司流程变更的时候可以使用 */
    @GetMapping("/suspendProcessInstance")
    public void suspendProcessInstance() {
        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到runtimeservice对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.得到ProcessDefinitionQuery对象,可以认为就是一个查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        //4.设置查询参数，查询出定义的流程，流程定义的部署id， id在act_re_deployment
        ProcessDefinition holiday = processDefinitionQuery.processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()   //设置排序方式，根据流程定义的版本号进行排序
                .desc()
                .singleResult();   //如果只有一个结果，
//                .list();
        //是否被暂停， true：是，false：否
        boolean suspended = holiday.isSuspended();
        String instId = holiday.getId();
        if(suspended){
            //激活指定部署id的全部实例，参数1：流程定义id，参数2：是否激活，参数3，激活时间点
            repositoryService.activateProcessDefinitionById(instId, true, null);//也可以用key，但是我们已经能获取到id
            System.out.println("流程定义实例："+instId+"激活");
        }else{
            //暂停指定部署id的全部实例
            repositoryService.suspendProcessDefinitionById(instId);
            System.out.println("流程定义实例："+instId+"挂起（暂停）");
        }

    }

    /** 单个启动或者暂停 实例， 当对单个实例审批操作的时候使用 */
    @GetMapping("/suspendProcessOneInstance")
    public void suspendProcessOneInstance() {
        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到runtimeservice对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3.得到ProcessDefinitionQuery对象,可以认为就是一个查询器
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();

        //4.设置查询参数，查询出定义的流程，流程实例id， id在act_ru_execution, 字段为PROC_INST_ID_
        ProcessInstance processInstance = processInstanceQuery.processInstanceId("12511").singleResult();

        //是否被暂停， true：是，false：否
        boolean suspended = processInstance.isSuspended();
        String instId = processInstance.getId();
        if(suspended){
            //激活指定部署id的全部实例
            runtimeService.activateProcessInstanceById(instId);//也可以用key，但是我们已经能获取到id
            System.out.println("流程定义实例："+instId+"激活");
        }else{
            //暂停指定部署id的全部实例
            runtimeService.suspendProcessInstanceById(instId);
            System.out.println("流程定义实例："+instId+"挂起（暂停）");
        }

    }


}
