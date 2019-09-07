package com.javasea.browser;

import com.javasea.browser.support.SimpleResponse;
import com.javasea.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName BrowserSecurityController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/28 0028 22:31
 */
@RestController
public class BrowserSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @Description 当需要认证时跳转到这里，且根据测试时访问的url类型进行跳转还是返回json
     * @Author longxiaonan@163.com
     * @Date 22:34 2019/6/28 0028
     **/
    @GetMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取引发跳转的请求
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            //引发跳转请求url
            String targetUrl = savedRequest.getRedirectUrl();
            if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
                //如果访问的请求是“http://localhost:8080/index.html”，那么会进入到这里
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getSignInPage());
            }
        }
        return new SimpleResponse("访问的用户需要认证，请引导用户到登录页面");
    }
}
