package com.zhirui.lmwy.common.exception;

import com.zhirui.lmwy.common.exception.impl.AuthenticationException;
import com.zhirui.lmwy.common.exception.impl.BusinessException;
import com.zhirui.lmwy.common.exception.impl.ParamException;
import org.springframework.util.Assert;

/**
 * @ClassName Exceptions
 * @Description exception工具类
 * @Author longxiaonan@163.com
 * @Date 2018/8/15 22:25
 */
public class Exceptions {

    /**
     * 抛出认证异常
     */
    public static RuntimeException throwAuthenticationException(String msg) {
        throw new AuthenticationException(msg);
    }

    /**
     * 抛出参数异常
     * 当参数校验错误时抛出，如不为空的字段参数是空等参数手动校验不通过等等
     */
    public static RuntimeException throwParamException(String msg) {
        throw new ParamException(msg);
    }

    /**
     * 抛出参数异常 ，在第二个参数data可以存放校验失败的属性返回到前端, 推荐data为map类型
     */
    public static RuntimeException throwParamException(String msg, Object data) {
        throw new ParamException(msg, data);
    }

    /**
     * 抛出业务异常
     * 业务逻辑非预期结果
     */
    public static RuntimeException throwBusinessException(String msg) {
        throw new BusinessException(msg);
    }

}
