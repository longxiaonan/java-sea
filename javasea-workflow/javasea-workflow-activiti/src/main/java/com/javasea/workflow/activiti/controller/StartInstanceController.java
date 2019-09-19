package com.javasea.workflow.activiti.controller;

import com.javasea.workflow.activiti.entity.Holiday;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ActivitiStartInstance
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/8/20 0020 22:14
 */
@RestController
public class StartInstanceController {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    /** 启动实例 涉及到的表：
     * act_hi_actinst：已完成的活动信息
     * act_hi_identitylink：参与者信息
     * act_hi_procinst：流程实例
     * act_hi_taskinst：任务实例
     * act_ru_execution：执行表
     * act_ru_identitylink：当前参与者信息
     * act_ru_task：任务
     *
     * 用下面两个表存储流程变量
     * act_ge_bytearray:
     * act_ru_variable:
     * */
    @GetMapping("/startInstance")
    public void startInstance(){

        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到runtimeservice对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3.根据流程定义的key，创建流程实例。可以打开bpmn文件，在左边的id位置找到key，
        //3.1 创建流程实例方式1：
        // 也可以在数据的`act_re_procdef`表的KEY_列中找到。
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday");

        //3.2 创建流程实例方式2：
        //第一个参数是部署id，第二个参数是act_ru_execution表中的业务id，用于关联业务表的记录id
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday", "1001");

        //3.3 创建流程实例方式3:
        //设置Assignee UEL的值，在bpmn的流程图（见holiday2.bpmn），各个流程的执行人分别是${assignee0},${assignee1},${assignee2}
//        Map<String,Object> map = new HashMap<>();
//        map.put("assignee0","zhangsan");
//        map.put("assignee1","lishi");
//        map.put("assignee2","wangwu");
////        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday2", map);
//        //第二个参数是businessKey
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday2","10001", map);

        //3.4 创建流程实例方式4：
        //设置条件的UEL值，在bpmn的流程图（见holiday4.bpmn），在流程连线上设置${holiday.num<=3}，${holiday.num>3}
        String key = "myProcess_1"; //holiday4.bpmn的流程定义id
        Map<String,Object> map = new HashMap<>();
        Holiday holiday = new Holiday();
        holiday.setNum(1F);
        map.put("holiday", holiday);
        //在实例启动的时候设置流程变量
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(key,"10002", map);
        //还可以在通过实例id设置流程变量
//        runtimeService.setVariable(instance.getId(), "holiday", map);

        //4.输出相关信息, 在表`act_hi_taskinst`可以找到流程实例相关的信息
        System.out.println("流程部署id："+instance.getDeploymentId());//null
        System.out.println("流程进程id："+instance.getProcessInstanceId());//null
        System.out.println("流程定义id："+instance.getProcessDefinitionId());//myProcess_1:3:2508 见表`act_re_procdef`
        System.out.println("流程实例id："+ instance.getId());//5005 见表`act_hi_taskinst`
        System.out.println("活动id："+instance.getActivityId());//null
        System.out.println("流程实例name："+instance.getName());//null
    }
}
