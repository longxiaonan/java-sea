package com.javasea.workflow.activiti.controller;

import org.activiti.api.runtime.shared.query.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ActivitiController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/19 0019 22:02
 */
@RestController
public class ActivitiController {

    @Autowired
    private RepositoryService repositoryService;

}
