package com.zhirui.lmwy.common.persistence.model.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

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
@Builder
@ApiModel(value="返回值类型对象")
public class ResultModel<T> implements Serializable {

    /** 定义jackson对象 */
//    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** 响应业务状态 */
    @ApiModelProperty(value="返回的code" ,required=true)
    private Integer code;

    /** 响应消息 */
    @ApiModelProperty(value="响应的消息")
    private String msg;

    /** 响应中的数据 */
    private T data;

    private Date time;

    // 堆栈信息
//    private StackTraceElement[] debug;

    public static ResultModel ok(String msg, Object data) {
        return ResultModel.build(ResultCodeEnum.SUCCESS, msg, data);
    }

    public static ResultModel ok(String msg) {
        return ResultModel.build(ResultCodeEnum.SUCCESS, msg, null);
    }

    public static ResultModel ok(Object data) {
        return ResultModel.build(ResultCodeEnum.SUCCESS, null, data);
    }

    public static ResultModel ok() {
        return ResultModel.build(ResultCodeEnum.SUCCESS, null, null);
    }


    public static ResultModel error() {
        return ResultModel.build(ResultCodeEnum.SERVER_ERROR, null, null);
    }

    public static ResultModel error(String msg) {
        return ResultModel.build(ResultCodeEnum.SERVER_ERROR, msg, null);
    }

    public static ResultModel error(String msg, Object data) {
        return ResultModel.build(ResultCodeEnum.SERVER_ERROR, msg, data);
    }

    public static ResultModel error(ResultCodeEnum codeEnum) {
        return ResultModel.build(codeEnum, null, null);
    }

    public static ResultModel error(Object data) {
        return ResultModel.build(ResultCodeEnum.SERVER_ERROR, null, data);
    }

    public static ResultModel error(ResultCodeEnum codeEnum, String msg) {
        return ResultModel.build(codeEnum, msg, null);
    }

    public static ResultModel error(ResultCodeEnum codeEnum, String msg, Object data) {
        return ResultModel.build(codeEnum, msg, data);
    }

    /**
     * @Description 用户信息错误,token信息校验异常,
     **/
    public static ResultModel errorTokenMsg(String msg) {
        return ResultModel.build(ResultCodeEnum.AUTHENTICATION_ERROR, msg, null);
    }

//    /**
//     * @Description 异常信息返回,且返回堆栈信息
//     * @param stackTrace 堆栈信息
//     * @param msg 错误的信息提示
//     **/
//    public static ResultModel error(Integer status, String msg, StackTraceElement[] stackTrace) {
//        return new ResultModel(status, msg, stackTrace);
//    }

    public static ResultModel build(ResultCodeEnum codeEnum, String msg, Object data){
        String message = codeEnum.getMsg();
        if (StringUtils.isNotBlank(msg)){
            message = msg;
        }
        return ResultModel.builder()
                .code(codeEnum.getCode())
                .msg(message)
                .data(data)
                .time(new Date())
                .build();
    }

    public static ResultModel resultQuery(boolean flag, Object data){
        return resultMsg(flag, flag ? ResultMsg.QUERY_SUCCESS : ResultMsg.QUERY_FAIL, data);
    }
    public static ResultModel resultInsert(boolean flag){
        return resultMsg(flag, flag ? ResultMsg.INSERT_SUCCESS : ResultMsg.INSERT_FAIL);
    }
    public static ResultModel resultUpdate(boolean flag){
        return resultMsg(flag, flag ? ResultMsg.UPDATE_SUCCESS : ResultMsg.UPDATE_FAIL);
    }
    public static ResultModel resultDelete(boolean flag){
        return resultMsg(flag, flag ? ResultMsg.DELETE_SUCCESS : ResultMsg.DELETE_FAIL);
    }
    public static ResultModel result(boolean flag){
        return resultMsg(flag, null);
    }

    public static ResultModel resultMsg(boolean flag, String msg){
        if (flag){
            return ok(msg);
        }
        return error(msg);
    }

    public static ResultModel resultMsg(boolean flag, String msg, Object data){
        if (flag){
            return ok(msg, data);
        }
        return error(msg, data);
    }

}
