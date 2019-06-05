package com.iee.webbase.config;

import com.iee.webbase.common.BusinessException;
import com.iee.webbase.common.ResultBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 如果是浏览器访问的则跳转到 /error 页面，否则返回 json 格式错误信息
 * 两种实现方式：
 * 1.在类上添加注解@ResponseBody，异常处理方法上直接返回一个对象，见方法handleBusinessException2
 * 2.在类上添加注解@Controller和@RequestMapping("/globalError")，实现springmvc性质的返回
 * @author longxiaonan@163.com
 */
@Slf4j
@Controller
@ControllerAdvice
@RequestMapping("/globalError")
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String FORWARD_GLOBAL_ERROR = "forward:/globalError";
    private final HttpServletRequest request;

    /**
     * 统一处理参数校验异常
     * <pre>
     * 对象参数接收请求体校验不通过会抛出 MethodArgumentNotValidException
     * 普通参数校验校验不通过会抛出 ConstraintViolationException
     * 必填参数没传校验不通过会抛出 ServletRequestBindingException
     * 请求参数绑定到对象上校验不通过会抛出 BindException
     * </pre>
     */
    @ExceptionHandler({ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            ServletRequestBindingException.class,
            BindException.class})
    public ModelAndView handleValidationException(Exception e) {
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
            return handleUnknownException(e);
        }
        log.warn("参数校验不通过, {}, msg: {}", logMsg, msg);
        return failResultModelAndView(msg);
    }

    private String getBindingResultMsg(BindingResult result) {
        return result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    /**
     * 统一处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusinessException(BusinessException t) {
        String logMsg = getErrorLogMsg(t);
        log.error("捕获到业务异常, {}, msg: {}", logMsg, t.getMessage());
        return failResultModelAndView(t.getMessage());
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResultBean<Object> handleBusinessException2(BusinessException t) {
//        String logMsg = getErrorLogMsg(t);
//        log.error("捕获到业务异常, {}, msg: {}", logMsg, t.getMessage());
//        return ResultBean.fail("发生异常！");
//    }

    private ModelAndView failResultModelAndView(String msg) {
        Map<String, Object> model = new HashMap<>(4);
        model.put("msg", msg);
        model.put("code", ResultBean.FAIL);
        return new ModelAndView(FORWARD_GLOBAL_ERROR, model);
    }

    /**
     * 统一处理未知异常
     */
    @ExceptionHandler
    public ModelAndView handleUnknownException(Throwable t) {
        String logMsg = getErrorLogMsg(t);
        // 未知异常
        log.error("捕获到未经处理的未知异常, {}", logMsg, t);
        return new ModelAndView(FORWARD_GLOBAL_ERROR, "msg", "服务发生异常");
    }

    /**
     * 处理浏览器的 html 直接请求
     */
    @RequestMapping(produces = "text/html")
    public String errorHtml() {
        String msg = Objects.toString(request.getAttribute("msg"), "出错啦");
        request.setAttribute("msg", msg);
        return "error";
    }

    /**
     * 其他请求则以 response body 的形式返回
     */
    @ResponseBody
    @RequestMapping
    public ResultBean<?> error() {
        String msg = Objects.toString(request.getAttribute("msg"), "出错啦");
        String code = Objects.toString(request.getAttribute("code"), null);
        // 如果有指定 code，则按 code 返回
        if (StringUtils.isNumeric(code)) {
            return new ResultBean<>(Integer.parseInt(code), msg);
        } else {
            // 否则视为未知异常
            return ResultBean.unknownError(msg);
        }
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
}
