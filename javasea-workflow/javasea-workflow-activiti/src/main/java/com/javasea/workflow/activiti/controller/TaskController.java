package com.javasea.workflow.activiti.controller;

import com.javasea.workflow.activiti.entity.Holiday;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ActivitiTaskQuery
 * @Description 进行任务的查询和完成
 * @Author longxiaonan@163.com
 * @Date 2019/8/20 0020 22:14
 */
@RestController
public class TaskController {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    /** 根据流程定义的key, 流程节点的负责人，实现当前用户的任务列表查询。比如查询张三当前的任务，是需要填写请假单
     * 相关表：act_hi_actinst
     * */
    @GetMapping("/taskQuery")
    public void startInstance(){

        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到taskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.根据流程定义的key, 流程节点的负责人，实现当前用户的任务列表查询。可以打开bpmn文件，在左边的id位置找到key,
        // 也可以在数据的`act_re_procdef`表的KEY_列中找到。
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee("zhangsan")
                .list();

        //4.任务列表的展示
        for (Task task : list) {
            System.out.println("流程实例id："+task.getProcessInstanceId());//act_hi_procinst
            System.out.println("任务id："+task.getId());//act_hi_taskinst 的 id
            System.out.println("任务负责人名称："+task.getAssignee());//act_hi_taskinst
            System.out.println("任务名称："+task.getName());//act_hi_taskinst
        }
    }

    /** 根据流程定义的key, 流程节点的负责人，实现当前用户的任务列表查询。比如查询张三当前的任务，是需要填写请假单
     * 如果流程实例在挂起的情况下，去complate会抛异常ActivitiException， 提示为 Cannot complate a suspended task
     * 相关表：
     * act_hi_actinst：当前任务节点结束，新增下任务节点记录
     * act_hi_identitylink：参与者信息，新增下个任务节点的参与者信息
     * act_hi_taskinst：任务实例，任务结束，新增下任务节点记录，如果是最后一个任务结束，那么不增加记录，只添加了结束时间。
     *      跟act_hi_actinst比，少了startevent节点记录
     * act_ru_execution：新增了一条记录
     * act_ru_identitylink：当前任务节点的参与者信息
     * act_ru_task：记录替换成了当前需要执行的任务。如果流程结束，那么任务删除
     * */
    @GetMapping("/taskComplate")
    public void taskComplate(){

        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到taskService对象
        TaskService taskService = processEngine.getTaskService();
        //3.处理任务，结合当前用户任务列表查询操作的话，任务的id：12515
        //表 act_hi_taskinst 中会该任务会完成，且有完成时间，然后会多一条记录
//        taskService.complete("12515");//张三完成“填写申请单”
        taskService.complete("2");//李四完成“部门经理审批”
    }


    @GetMapping("/taskQueryAndComplate")
    public void test(){
        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到taskService对象
        TaskService taskService = processEngine.getTaskService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3.根据流程定义的key, 流程节点的负责人，实现当前用户的任务列表查询。可以打开bpmn文件，在左边的id位置找到key,
        // 也可以在数据的`act_re_procdef`表的KEY_列中找到。查询的表是act_hi_taskinst
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("holiday-exclusive")
                //通过负责人来查询
                .taskAssignee("lisi")
//                .taskCandidateOrAssigned("zhangsan")//通过候选用户组的人员来查询
//                .singleResult();
                .list();


        for(Task task : taskList) {
            //组任务时，给某个用户领取任务, param1：taskId；assigness：候选人。将候选用户变成负责人的过程
//            taskService.claim(taskId, "zhangsan");
            //组任务时，给某个用户领取任务后，想要退回, 直接设置assignee为null就可以了。如果交接给别的候选人，那么用别的候选人(如lisi)替换掉null即可
//            taskService.setAssignee(taskId, null);
            Map<String,Object> map = new HashMap<>();
            Holiday holiday = new Holiday();
            holiday.setNum(1F);
            // ${holiday.num<=3}
            map.put("holiday", holiday);
            //通过任务id设置流程变量
//            taskService.setVariable(task.getId(), "assign0", "张三");
//            taskService.setVariable(task.getId(), "holiday", holiday);
            //通过任务id设置局部流程变量, 局部变量只能在该任务结束前使用，任务结束后该变量无法在当前流程实例使用，可以通过查询历史任务查询。
//            taskService.setVariableLocal(task.getId(), "holiday", holiday);
//            taskService.setVariablesLocal(task.getId(), map);
            //4.得到任务的流程实例id
            String processInstanceId = task.getProcessInstanceId();

            //5.通过流程实例id得到流程实例对象。查询的表是act_hi_procinst
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            //6.通过流程实例对象得到BusinessKey
            String businessKey = processInstance.getBusinessKey();

            //7.根据businessKey可以得到请假单信息
            System.out.println("businessKey: " + businessKey);

            //8.任务完结
            if (task != null) {
                taskService.complete(task.getId());
                //还可以在执行任务的时候设置流程变量
//                Map<String,Object> map = new HashMap<>();
//                Holiday holiday = new Holiday();
//                holiday.setNum(1F);
//                map.put("holiday", holiday);
//                taskService.complete(task.getId(),map);

                System.out.println("任务完成！！！");
            }else{
                System.out.println("未查到任务！！！");
            }
        }
    }

}
