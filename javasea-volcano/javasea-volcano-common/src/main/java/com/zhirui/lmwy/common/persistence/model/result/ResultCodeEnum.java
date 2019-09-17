package com.zhirui.lmwy.common.persistence.model.result;

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

    SUCCESS(200, "操作成功"),
    AUTHENTICATION_ERROR(401, "认证信息异常"),
    NOT_PERMISSION(403, "没有权限"), //没有权限
    NOT_FOUND(404, "你请求的路径不存在"),//你请求的路径不存在
    SERVER_ERROR(500, "操作失败"),
    PARAM_CHECK_ERROR(501, "请求参数校验异常"),
    OTHER(555, "系统异常");

    private final Integer code;
    private final String msg;
    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultCodeEnum getApiCode(int code) {
        ResultCodeEnum[] ecs = ResultCodeEnum.values();
        for (ResultCodeEnum ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
