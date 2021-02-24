package com.sudoku.system.security;

import cn.hutool.core.util.ArrayUtil;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.core.domain.CommonResult;
import com.sudoku.common.tools.ServletUtils;
import com.sudoku.system.security.filter.JwtAuthenticationTokenFilter;
import com.sudoku.system.security.handler.CustomizeLogoutSuccessHandler;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置Spring Security项
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomizeLogoutSuccessHandler logoutSuccessHandler;

  private final JwtAuthenticationTokenFilter authenticationTokenFilter;

  @Resource
  private UserDetailsService userDetailsService;

  @Value("${management.allow-ips}")
  private String[] requestActuatorIpList;

  public SecurityConfig(CustomizeLogoutSuccessHandler logoutSuccessHandler,
      JwtAuthenticationTokenFilter authenticationTokenFilter) {
    this.logoutSuccessHandler = logoutSuccessHandler;
    this.authenticationTokenFilter = authenticationTokenFilter;
  }

  /**
   * 获取密码编码器
   *
   * @return BCrypt密码编码器
   */
  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 解析IP列表
   *
   * @param ipList IP列表
   * @return Spring Security对应的EL表达式
   */
  private static String resolveIpList(String[] ipList) {
    return ArrayUtil.isEmpty(ipList) ? "" : IntStream.range(1, ipList.length)
        .mapToObj(i -> "or hasIpAddress('" + ipList[i] + "')")
        .collect(Collectors.joining("", "hasIpAddress('" + ipList[0] + "')", ""));
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
   * 设置用户服务对象
   *
   * @param auth 身份验证管理器生成器
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(SecurityConfig.passwordEncoder());
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
    disableSecurityHeader(security);
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
    security.logout().logoutUrl("/security/logout").logoutSuccessHandler(logoutSuccessHandler);
  }

  /**
   * 处理认证异常
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void handlerAuthenticationException(HttpSecurity security) throws Exception {
    security.exceptionHandling()
        .authenticationEntryPoint(
            (request, response, exception) -> ServletUtils.returnJsonData(response, CommonResult.error(StatusCode.USER_NOT_AUTHORITY)));
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
   * 过滤请求
   *
   * @param security Http安全对象
   * @throws Exception 异常
   */
  private void filterRequests(HttpSecurity security) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry requests = security.authorizeRequests();
    permitLoginRequest(requests);
    permitRegisterRequest(requests);
    authenticateActuatorRequest(requests);
    authenticateOtherRequest(requests);
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
   * 允许登录、获取验证码图片请求无限制访问
   *
   * @param requests 注册表对象
   */
  private void permitLoginRequest(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry requests) {
    requests.antMatchers("/security/login", "/security/captchaImage").permitAll();
  }

  /**
   * 允许注册请求无限制访问
   *
   * @param requests 注册表对象
   */
  private void permitRegisterRequest(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry requests) {
    requests.antMatchers("/user/register").permitAll();
  }

  /**
   * 限制请求Actuator资源的用户IP
   *
   * @param requests 注册表对象
   */
  private void authenticateActuatorRequest(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry requests) {
    requests.antMatchers("/actuator/**").access(resolveIpList(requestActuatorIpList));
  }

  /**
   * 其他请求都需要认证授权
   *
   * @param requests 注册表对象
   */
  private void authenticateOtherRequest(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry requests) {
    requests.anyRequest().authenticated();
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
    security.ignoring().antMatchers("/swagger-ui.html",
        "/swagger-ui/*",
        "/swagger-resources/**",
        "/v2/api-docs",
        "/v3/api-docs",
        "/webjars/**");
  }

  /**
   * 禁用Headers
   *
   * @param security Web安全对象
   * @throws Exception 异常
   */
  private void disableSecurityHeader(HttpSecurity security) throws Exception {
    security.headers().disable();
  }
}
