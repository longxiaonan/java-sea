package com.zhirui.lmwy.common.model;

/**
 * @ClassName ErrorStatusEnum
 *  * 				200：表示成功
 *  * 				401：拦截器拦截到用户token出错
 *  * 				500：表示错误，错误信息在msg字段中
 *  * 				501：bean验证错误，不管多少个错误都以map形式返回,参数校验错误等
 *  * 				555：异常抛出信息
 * @Description 异常类型枚举
 * @Author longxiaonan@163.com
 * @Date 2018/7/13 12:10
 */
public enum ResultCodeEnum {

    SUCCESS(200),
    AUTHENTICATION_ERROR(401),
    SERVER_ERROR(500),
    PARAM_CHECK_ERROR(501),
    OTHER(555);

    private Integer code;
    ResultCodeEnum(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}
