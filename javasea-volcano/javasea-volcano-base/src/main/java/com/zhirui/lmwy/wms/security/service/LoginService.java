
package com.zhirui.lmwy.wms.security.service;


import com.zhirui.lmwy.common.persistence.model.result.ResultModel;
import com.zhirui.lmwy.wms.security.param.LoginParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  登录服务接口
 * </p>
 * @auth longxiaonan@aliyun.com
 * @date 2019-05-23
 **/
public interface LoginService {

    ResultModel login(LoginParam loginParam);

    void refreshToken(HttpServletResponse response, Jws<Claims> jws);

}
