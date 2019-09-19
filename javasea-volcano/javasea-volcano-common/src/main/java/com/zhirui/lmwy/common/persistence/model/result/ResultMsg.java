package com.zhirui.lmwy.common.persistence.model.result;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel("返回信息")
public interface ResultMsg extends Serializable {
    String QUERY_SUCCESS = "查询成功！";
    String QUERY_FAIL = "查询失败！";
    String INSERT_SUCCESS = "新增成功！";
    String INSERT_FAIL = "新增失败！";
    String UPDATE_SUCCESS = "更新成功！";
    String UPDATE_FAIL = "更新失败！";
    String DELETE_SUCCESS = "删除成功！";
    String DELETE_FAIL = "删除失败！";
    String CATCHEXCEPTION = "捕获到异常！";
    String INCONFORMITY = "参数不符合要求！";
    String SERVICE_ERROR = "服务异常，请与管理员联系！";
}
