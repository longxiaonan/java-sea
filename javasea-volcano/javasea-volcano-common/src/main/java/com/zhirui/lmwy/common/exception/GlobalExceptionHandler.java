package com.zhirui.lmwy.common.exception;

import com.zhirui.lmwy.common.exception.impl.AuthenticationException;
import com.zhirui.lmwy.common.exception.impl.BusinessException;
import com.zhirui.lmwy.common.exception.impl.ParamException;
import com.zhirui.lmwy.common.persistence.model.result.ResultCodeEnum;
import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description controller的通用异常处理类，会自动监听controller抛出的异常，然后到相应的异常类中进行处理
 * @Author longxiaonan@163.com
 **/

@Slf4j
@Controller
@ControllerAdvice
@ResponseBody
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    /**
     * 通用异常处理, 抛出堆栈信息++
     */
    @ExceptionHandler(value = Exception.class)
    public Object commonExceptionHandler(HttpServletRequest reqest,
                                         HttpServletResponse response, Exception e) {
        log.error("", e);
//        return ResultModel.error(ResultCodeEnum.SERVER_ERROR, e.getMessage(), e.getStackTrace());
        return ResultModel.error(ResultCodeEnum.SERVER_ERROR, e.getMessage(), null);
    }

    /**
     * 认证异常处理，被手动抛出
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object authenticationExceptionHandler(HttpServletRequest reqest,
                                                 HttpServletResponse response, Exception ex) {
        log.error("", ex);
        return ResultModel.errorTokenMsg(ex.getMessage());
    }

    /**
     * 业务异常, 通过Assert或者Objects等校验参数异常(NPT等其他异常)处理
     */
    @ExceptionHandler(value = BusinessException.class)
    public Object businessExceptionHandler(HttpServletRequest reqest,
                                          HttpServletResponse response, Exception ex) {

        return ResultModel.error(ResultCodeEnum.OTHER, ex.getMessage());
    }

    /**
     * 统一处理参数校验异常
     * <pre>
     * 普通参数校验校验不通过会抛出 ConstraintViolationException, controller方法上的@Min等注解校验时候抛出
     * 对象参数接收请求体校验不通过会抛出 MethodArgumentNotValidException，controller post方法上对实体校验添加@Validated的时候抛出
     * 必填参数没传校验不通过会抛出 ServletRequestBindingException
     * 请求参数绑定到对象上校验不通过会抛出 BindException，controller get方法上对实体校验添加@Validated的时候抛出
     * </pre>
     */
    @ExceptionHandler({ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            ServletRequestBindingException.class,
            BindException.class,
            ParamException.class,
            TypeMismatchException.class,
            IllegalArgumentException.class    //如果手动校验参数，手动抛出该异常
    })
    public ResultModel handleValidationException(Exception e) {
        log.error("参数异常捕获", e); //记录日志
        String logMsg = getErrorLogMsg(e);
        String msg = "";
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException t = (MethodArgumentNotValidException) e;
            msg = getBindingResultMsg(t.getBindingResult());
        } else if (e instanceof BindException) {
            BindException t = (BindException) e;
            msg = getBindingResultMsg(t.getBindingResult());
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException t = (ConstraintViolationException) e;
            msg = t.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException t = (MissingServletRequestParameterException) e;
            msg = t.getParameterName() + " 不能为空";
        } else if (e instanceof MissingPathVariableException) {
            MissingPathVariableException t = (MissingPathVariableException) e;
            msg = t.getVariableName() + " 不能为空";
        } else {
            // 其他类型的错误当成未知异常处理
            return ResultModel.error(ResultCodeEnum.PARAM_CHECK_ERROR, e.getMessage());
        }
        log.warn("参数校验不通过, {}, msg: {}", logMsg, msg);
        return ResultModel.error(ResultCodeEnum.PARAM_CHECK_ERROR, msg);
    }

    /**
     * 异常信息应包含 url + queryString(若有) + 请求参数(这里只能拿到表单提交的参数) + username(若有)
     */
    private String getErrorLogMsg(Throwable t) {
        StringBuilder errorLogMsg = new StringBuilder();
        // url，包括查询 queryString
        errorLogMsg.append("url: ").append(request.getRequestURL().toString());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            errorLogMsg.append("?").append(request.getQueryString());
        }
        // 获取参数，这里只能拿到查询参数和以表单形式提交的参数，requestBody 的拿不到
        Map<String, String[]> params = request.getParameterMap();
        if (params != null && !params.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            params.forEach((k, v) -> {
                builder.append(",").append(k).append("=").append(Arrays.toString(v));
            });
            errorLogMsg.append(", params:").append(builder.substring(1));
        }
        // 如果能获取到当前登录人信息，则添加到最前面
        String username = getUsername();
        if (StringUtils.isNotBlank(username)) {
            errorLogMsg.insert(0, "username: " + username + ", ");
        }
        return errorLogMsg.toString();
    }

    private String getUsername() {
        // 尝试拿当前登录人的 principal
        return Optional.ofNullable(request.getUserPrincipal())
                .map(Principal::getName)
                .orElse("");
    }

    private String getBindingResultMsg(BindingResult result) {
        return result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

}
