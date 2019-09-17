/**
 * Copyright 2019-2029 longxiaonan(https://github.com/longxiaonan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhirui.lmwy.common.utils.web;

import com.zhirui.lmwy.common.persistence.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;


/**
 * @author longxiaonan
 * @date 2018-11-08
 */
@Slf4j
public class LoginUtil {

    /**
     * TOKEN有效时间，单位分钟
     */
    public static Integer TOKEN_VALID_TIME_MINUTE;

    static {
        Environment environment = SpringContextUtils.getBean(Environment.class);
        String time = environment.getProperty("custom.login.token.valid.time.minute");
        TOKEN_VALID_TIME_MINUTE = Integer.valueOf(time);
        log.info("TOKEN_VALID_TIME_MINUTE:{}",TOKEN_VALID_TIME_MINUTE);
    }

    /**
     * 从请求头或者请求参数中
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        // 从请求头中获取token
        String token = request.getHeader(CommonConstant.TOKEN);

        String authToken = request.getHeader(CommonConstant.Authorization);
        if (StringUtils.isBlank(authToken)){
            // 从请求参数中获取token
            authToken = request.getParameter(CommonConstant.TOKEN);
        }
        return authToken;
    }

}
