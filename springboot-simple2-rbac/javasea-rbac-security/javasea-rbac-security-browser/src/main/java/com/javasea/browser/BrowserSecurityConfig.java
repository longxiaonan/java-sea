package com.javasea.browser;

import com.javasea.security.core.authentication.FormAuthenticationConfig;
import com.javasea.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.javasea.security.core.validate.code.ValidateCodeSecurityConfig;
import com.javasea.security.core.properties.SecurityConstants;
import com.javasea.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @ClassName BrowserSecurityConfig
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/28 0028 22:08
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    /**
     * 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 表单登陆配置
     */
    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        formAuthenticationConfig.configure(http);

        http.apply(validateCodeSecurityConfig)//校验验证码的过滤器配置
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)//短信登陆过滤器配置, 配置token和provider等等
                .and()
                //记住我配置，如果想在'记住我'登录时记录日志，可以注册一个InteractiveAuthenticationSuccessEvent事件的监听器
                .rememberMe()//后面开始配置rememberMe功能
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                //除了antMatchers匹配的url可以无需授权，其他的url都需要授权
                .authorizeRequests().antMatchers(
                        //无授权需要跳转到的url
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                            //web登陆时访问的url
                            SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                            //app登陆时访问的url
                            SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                            //登陆页面地址
                            securityProperties.getBrowser().getSignInPage(),
                            //验证码请求地址
                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*").permitAll().anyRequest().authenticated()
                .and()
                .csrf().disable();//暂时关闭csrf功能


//        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
//        validateCodeFilter.setAuthenticationFailureHandler(imoocAuthenticationFailureHandler);
//        validateCodeFilter.setSecurityProperties(securityProperties);
//        validateCodeFilter.afterPropertiesSet();
//
//        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)//将验证码过滤器添加到用户名和密码校验前面
//                .formLogin()//表单模式
////                .loginPage("/imooc-signIn.html")//指定登陆页面
//                .loginPage("/authentication/require")//指定登陆页面
//                .loginProcessingUrl("/authentication/form")//"imooc-signIn.html"页面表单提交认证的接口的地址，指定后security就知道这个是自定义的认证的地址
//                .successHandler(imoocAuthenticationSuccessHandler)
//                .failureHandler(imoocAuthenticationFailureHandler)
////      http.httpBasic()//弹窗模式
//                .and()
//                .authorizeRequests()//请求授权
////                .antMatchers("/imooc-signIn.html").permitAll()//登陆页面不认证
//                .antMatchers("/authentication/require",
//                        securityProperties.getBrowser().getLoginPage(),
//                        "/code/*").permitAll()
//                .anyRequest()//任何请求
//                .authenticated()//都需要认证
//                .and()
//                .csrf().disable();//关闭跨站请求伪造防护
    }

    /**
     * 记住我功能的token存取器配置
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
