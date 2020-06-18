package com.sudoku.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 登录过滤器
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  /**
   * 身份验证
   *
   * @param request  请求对象
   * @param response 响应对象
   * @return 认证方式
   * @throws AuthenticationException 认证异常
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    //登录请求限制为POST方式
    if (!request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
    }

    //获取请求数据
    Map<String, String> loginData = new HashMap<>();
    try {
      loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
    } catch (IOException e) {
      log.error("[attemptAuthentication][{}]", "读取登录请求数据时发生异常", e);
    }

    //获取用户名和密码
    String username = loginData.get(getUsernameParameter());
    String password = loginData.get(getPasswordParameter());
    if (username == null) {
      username = "";
    }
    if (password == null) {
      password = "";
    }
    username = username.trim();

    //构造认证Token
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
    setDetails(request, token);

    return this.getAuthenticationManager().authenticate(token);
  }
}
