package com.zhirui.lmwy.wms.demo.web.controller;

import com.zhirui.lmwy.common.utils.web.WebUtils;
import com.zhirui.lmwy.wms.demo.web.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName TestWebUtis
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/9/5 0005 21:09
 */
@RestController
public class TestWebUtis {

    @GetMapping("testWebUtils")
    public void test(HttpServletRequest request) throws IOException {

        HttpServletRequest rqs = WebUtils.getRequest();//rqs:org.apache.catalina.connector.RequestFacade@132ede2a
        System.out.println("rqs:" + rqs);
        if(rqs == null){
            rqs = request;
        }

        System.out.println(WebUtils.getRequest());//org.apache.catalina.connector.RequestFacade@132ede2a
        System.out.println(WebUtils.getResponse());//org.apache.catalina.connector.ResponseFacade@78851e0e
        System.out.println(WebUtils.getFullRequestUrl(WebUtils.getRequest()));//http://localhost:8801/testWebUtils
        System.out.println(WebUtils.getParametersStartingWith(WebUtils.getRequest(), "u"));//{}
        System.out.println(WebUtils.getRequestParams(WebUtils.getRequest()));//{}
        System.out.println(WebUtils.getServerBaseUrl());//http://localhost:8801
        System.out.println(WebUtils.getSession());//org.apache.catalina.session.StandardSessionFacade@587ff071
        System.out.println(WebUtils.getSubDomain(rqs));//localhost
        System.out.println(WebUtils.isAjaxRequest(rqs));//false
        //只能返回其中一个
//        WebUtils.writeResponse("11");
        WebUtils.writeResponse(User.builder().username("longxiaonan").age(18).build());

    }

}
