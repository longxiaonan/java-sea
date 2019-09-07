package com.zhirui.lmwy.common.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhirui.lmwy.common.exception.ReturnCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				401：拦截器拦截到用户token出错
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回,参数校验错误等
 * 				555：异常抛出信息
 * {
 * "msgqueue":"直接展示给终端用户的错误信息",
 * "code":"业务错误码",
 * "msg":"供开发者查看的错误信息",
 * "data":"响应中的数据"
 * "debug":["错误堆栈，必须开启 debug 才存在"  ]
 * }
 *
 * @author longxiaonan@163.com
 * @version V1.0
 */
@Data
public class ResultModel implements Serializable {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    // 堆栈信息
    private StackTraceElement[] debug;

    public static ResultModel build(Integer status, String msg, Object data) {
        return new ResultModel(status, msg, data);
    }

    public static ResultModel operateSuccess() {
        return ResultModel.ok(ResultMsg.OPERATE_SUCCESS);
    }

    public static ResultModel operateFail() {
        return new ResultModel(ResultMsg.OPERATE_FAIL);
    }

    public static ResultModel updateSuccess() {
        return ResultModel.ok(ResultMsg.UPDATE_SUCCESS);
    }

    public static ResultModel insertSuccess() {
        return new ResultModel(ResultMsg.INSERT_SUCCESS);
    }

    public static ResultModel deleteSuccess() {
        return new ResultModel(ResultMsg.DELETE_SUCCESS);
    }

    public static ResultModel updateFail() {
        return new ResultModel(ResultMsg.UPDATE_FAIL);
    }

    public static ResultModel insertFail() {
        return new ResultModel(ResultMsg.INSERT_FAIL);
    }

    public static ResultModel deleteFail() {
        return new ResultModel(ResultMsg.DELETE_FAIL);
    }

    public static ResultModel ok(String msg, Object data) {
        return new ResultModel(msg,data);
    }

    public static ResultModel ok(String msg) {
        return new ResultModel(msg);
    }

    public static ResultModel ok(Object data) {
        return new ResultModel(data);
    }

    public static ResultModel ok() {
        return new ResultModel(null);
    }


    public static ResultModel error() {
        return new ResultModel(ReturnCodeEnum.SERVER_ERROR.getCode(), ResultMsg.SERVICE_ERROR);
    }

    public static ResultModel error(String msg) {
        return new ResultModel(ReturnCodeEnum.SERVER_ERROR.getCode(), msg);
    }

    public static ResultModel error(Integer status, String msg) {
        return new ResultModel(status, msg);
    }

    /**
     * @Description 异常信息返回,且返回堆栈信息
     * @param stackTrace 堆栈信息
     * @param msg 错误的信息提示
     **/
    public static ResultModel error(Integer status, String msg, StackTraceElement[] stackTrace) {
        return new ResultModel(status, msg, stackTrace);
    }

    /**
     * @Description 异常信息返回，且携带错误的具体数据
     **/
    public static ResultModel error(Integer status, String msg, Object data) {
        return new ResultModel(status, msg, data);
    }

    /**
     * @Description 用户信息错误,token信息校验异常,
     **/
    public static ResultModel errorTokenMsg(String msg) {
        return new ResultModel(ReturnCodeEnum.AUTHENTICATION_ERROR.getCode(), msg);
    }

    private ResultModel(Integer code, String msg, StackTraceElement[] debug) {
        this.code = code;
        this.msg = msg;
        this.debug = debug;
    }

    private ResultModel(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultModel(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultModel(String msg, Object data) {
        this.code = ReturnCodeEnum.SUCCESS.getCode();
        this.msg = msg;
        this.data = data;
    }

    private ResultModel(String msg) {
        this.code = ReturnCodeEnum.SUCCESS.getCode();
        this.msg = msg;
        this.data = null;
    }

    private ResultModel(Object data) {
        this.code = ReturnCodeEnum.SUCCESS.getCode();
        this.msg = null;
        this.data = data;
    }

}
