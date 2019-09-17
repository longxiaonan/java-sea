
package com.zhirui.lmwy.wms.security.interceptor;

import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import com.zhirui.lmwy.common.utils.web.LoginUtil;
import com.zhirui.lmwy.common.utils.web.WebUtils;
import com.zhirui.lmwy.wms.security.service.LoginService;
import com.zhirui.lmwy.wms.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  判断jwt token是否有效
 * </p>
 * @auth longxiaonan@aliyun.com
 * @date 2019-05-22
 **/
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果访问的不是控制器,则跳出,继续执行下一个拦截器
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取token
        String token = LoginUtil.getToken(request);

        // 如果token为空，则s输出提示并返回
        if (StringUtils.isBlank(token)){
            ResultModel apiResult = ResultModel.errorTokenMsg("获取到的token异常");
            log.error("token is empty");
            WebUtils.writeResponse(apiResult);
            return false;
        }

        log.debug("token:{}",token);

        // 验证token是否有效
        Jws<Claims> jws = JwtUtil.verify(token);
        log.debug("token verify:{}",jws);
        if (jws == null){
            ResultModel apiResult = ResultModel.errorTokenMsg("token无效");
            log.error("token verify failed");
            WebUtils.writeResponse(apiResult);
            return false;
        }

        // 刷新token
        loginService.refreshToken(response, jws);

        // 存储jws对象到request对象中
        request.setAttribute("jws",jws);

        log.debug("token verify success");
        return true;
    }

}
