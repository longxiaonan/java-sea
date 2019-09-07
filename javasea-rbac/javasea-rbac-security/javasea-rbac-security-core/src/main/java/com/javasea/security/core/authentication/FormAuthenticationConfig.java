/**
 *
 */
package com.javasea.security.core.authentication;

import com.javasea.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 *
 * @author zhailiang
 */
@Component
public class FormAuthenticationConfig {

	@Autowired
	protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

	@Autowired
	protected AuthenticationFailureHandler imoocAuthenticationFailureHandler;

	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()    //登陆方式
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL) //登陆页面
				.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM) //后台api登陆url
				.successHandler(imoocAuthenticationSuccessHandler)  //成功处理器
				.failureHandler(imoocAuthenticationFailureHandler); //失败处理器
	}

}
