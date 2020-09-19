package com.sudoku.security;

import com.sudoku.security.filter.JwtAuthenticationTokenFilter;
import com.sudoku.security.handler.CustomizeLogoutSuccessHandler;
import com.sudoku.utils.SecurityUtils;
import com.sudoku.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置Spring Security项
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private CustomizeLogoutSuccessHandler logoutSuccessHandler;

  @Autowired
  private JwtAuthenticationTokenFilter authenticationTokenFilter;

  /**
   * 设置用户服务对象
   *
   * @param auth 身份验证管理器生成器
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(SecurityUtils.passwordEncoder());
  }

  /**
   * 解决无法直接注入AuthenticationManager
   *
   * @return 认证管理器
   * @throws Exception 异常
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * 设置过滤的地址
   *
   * @param security Web安全对象
   */
  @Override
  public void configure(WebSecurity security) {
    ignoreStaticResource(security);
    ignoreSwagger(security);
  }

  /**
   * 过滤所有的请求，除了configure(WebSecurity web)方法忽略的请求
   *
   * @param security Http安全对象
   * @throws Exception 异常对象
   */
  @Override
  protected void configure(HttpSecurity security) throws Exception {
    cancelCsrfProtection(security);
    handlerLogout(security);
    handlerAuthenticationException(security);
    cancelSession(security);
    filterRequests(security);
    setJWTFilter(security);
    security.headers().disable();
  }

  /**
   * 取消CSRF防护
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void cancelCsrfProtection(HttpSecurity security) throws Exception {
    security.csrf().disable();
  }

  /**
   * 处理登出
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void handlerLogout(HttpSecurity security) throws Exception {
    security.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
  }

  /**
   * 处理认证异常
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void handlerAuthenticationException(HttpSecurity security) throws Exception {
    security.exceptionHandling().authenticationEntryPoint((request, response, exception) -> {
      response.setStatus(401);
      ServletUtils.returnHome(request, response);
    });
  }

  /**
   * 取消使用Session，改用Token
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void cancelSession(HttpSecurity security) throws Exception {
    security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  /**
   * 过滤请求 除登录、验证码图片、注册允许匿名访问 其他均需认证
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void filterRequests(HttpSecurity security) throws Exception {
    security.authorizeRequests()
            .antMatchers("/security/login", "/security/captchaImage", "/user/register").permitAll()
            .anyRequest().authenticated();
  }

  /**
   * 设置JWT过滤器
   *
   * @param security Http安全对象
   */
  private void setJWTFilter(HttpSecurity security) {
    security.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * 忽略静态资源
   *
   * @param security Web安全对象
   */
  private void ignoreStaticResource(WebSecurity security) {
    security.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico");
  }

  /**
   * 忽略Swagger
   *
   * @param security Web安全对象
   */
  private void ignoreSwagger(WebSecurity security) {
    security.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
        "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**");
  }
}