package com.sudoku.config;

import com.sudoku.constant.enums.StatusCode;
import com.sudoku.convert.UserConvert;
import com.sudoku.model.po.User;
import com.sudoku.model.vo.CommonResult;
import com.sudoku.service.impl.UserDetailsServiceImpl;
import com.sudoku.utils.CoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置安全项
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  @Autowired
  private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
  @Autowired
  private CustomUrlDecisionManager customUrlDecisionManager;

  /**
   * 设置用户服务对象
   *
   * @param auth 身份验证管理器生成器
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(CoreUtils.passwordEncoder());
  }

  /**
   * 设置过滤的地址
   *
   * @param web 网络安全对象
   */
  @Override
  public void configure(WebSecurity web) {
    //不拦截静态资源
    web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/user/register", "/img/**", "/fonts/**", "/favicon.ico");
    //不拦截swagger
    web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
        "/swagger-resources/configuration/security", "/swagger-ui.html");
  }

  /**
   * 创建登录过滤器
   *
   * @return 登录过滤器
   * @throws Exception 异常对象
   */
  @Bean
  public LoginFilter loginFilter() throws Exception {
    LoginFilter loginFilter = new LoginFilter();
    //身份认证成功的回调方法
    loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
      //获取身份认证信息
      User user = (User) authentication.getPrincipal();
      user.setPassword(null);
      //返回消息
      CoreUtils.responseClient(response, CommonResult.success(user, "登录成功"));
    });
    //身份认证失败的回调方法
    loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
      String message = "登录时发生错误，请联系管理员！";
      if (exception instanceof BadCredentialsException) {
        message = "用户名或者密码输入错误，请重新输入!";
      } else if (exception instanceof CredentialsExpiredException) {
        message = "密码已过期，请联系管理员!";
      } else if (exception instanceof AccountExpiredException) {
        message = "账户已过期，请联系管理员!";
      } else if (exception instanceof DisabledException) {
        message = "账户被禁用，请联系管理员!";
      } else if (exception instanceof LockedException) {
        message = "账户被锁定，请联系管理员!";
      }
      //返回首页
      CoreUtils.returnHome(request, response);
      //返回消息
      CoreUtils.responseClient(response, CommonResult.error(StatusCode.LOGIN_ERROR, message));
    });
    //设置身份认证管理对象
    loginFilter.setAuthenticationManager(authenticationManagerBean());
    //设置需要认证的地址
    loginFilter.setFilterProcessesUrl("/doLogin");
    return loginFilter;
  }

  /**
   * 过滤所有的请求，除了configure(WebSecurity web)方法忽略的请求
   *
   * @param http 网络安全对象
   * @throws Exception 异常对象
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
          /**
           * 后处理
           *
           * @param object 初始化对象
           * @param <O>    泛型
           * @return 对象
           */
          @Override
          public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            //设置自定义的地址处理器
            object.setAccessDecisionManager(customUrlDecisionManager);
            //设置自定义的过滤器
            object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
            return object;
          }
        })
        .and()
        .logout()
        //注销成功的回调方法
        .logoutSuccessHandler((request, response, authentication) -> {
          //获取身份认证信息
          User user = (User) authentication.getPrincipal();
          //返回消息
          CoreUtils.responseClient(response, CommonResult.success(UserConvert.INSTANCE.convert(user), "注销成功"));
        })
        //会给没有登录的用户适配一个AnonymousAuthenticationToken
        .permitAll()
        .and()
        //取消csrf防护
        .csrf().disable().exceptionHandling()
        //处理没有认证，防止重定向
        .authenticationEntryPoint((request, response, exception) -> {
          response.setStatus(401);
          //返回首页
          CoreUtils.returnHome(request, response);
        });
    //设置登录过滤器
    http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
