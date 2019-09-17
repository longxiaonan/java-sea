package com.javasea.workflow.activiti.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName HistoryQuery
 * @Description 历史信息查询
 * @Author longxiaonan@163.com
 * @Date 2019/8/21 0021 21:38
 */
@RestController
public class HistoryQueryController {

    @Autowired
    StandaloneProcessEngineConfiguration standaloneProcessEngineConfiguration;

    @GetMapping("/historyQuery")
    public void historyQuery(){

        //1.得到processEngine
        ProcessEngine processEngine = standaloneProcessEngineConfiguration.buildProcessEngine();

        //2.得到HistoryService
        HistoryService historyService = processEngine.getHistoryService();

        //3.得到HistoryActivitiInstanceQuery对象
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();

        //3.1设置查询条件, act_hi_actinst表中获取
        historicActivityInstanceQuery.processInstanceId("12511");

        //4.执行查询
        List<HistoricActivityInstance> list = historicActivityInstanceQuery
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();

        //5.遍历查询结果
        list.forEach(inst ->{
            System.out.println(inst.getActivityId());
            System.out.println(inst.getActivityName());
            System.out.println(inst.getProcessDefinitionId());
            System.out.println(inst.getProcessInstanceId());
            System.out.println("===============================");
        });
    }

}
