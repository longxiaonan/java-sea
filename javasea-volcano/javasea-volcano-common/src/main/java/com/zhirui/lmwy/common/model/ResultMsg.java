package com.zhirui.lmwy.common.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel("返回信息")
public class ResultMsg implements Serializable {
    public static final String OPERATE_SUCCESS = "操作成功";
    public static final String OPERATE_FAIL = "操作失败";
    public static final String INSERT_SUCCESS = "新增成功";
    public static final String INSERT_FAIL = "新增失败";
    public static final String UPDATE_SUCCESS = "更新成功";
    public static final String UPDATE_FAIL = "更新失败";
    public static final String DELETE_SUCCESS = "删除成功";
    public static final String DELETE_FAIL = "删除失败";
    public static final String CATCHEXCEPTION = "捕获到异常";
    public static final String INCONFORMITY = "参数不符合要求";
    public static final String SERVICE_ERROR = "服务异常，请与管理员联系！";
}
