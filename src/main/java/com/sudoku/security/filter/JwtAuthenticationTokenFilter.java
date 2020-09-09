package com.sudoku.security.filter;

import com.sudoku.security.model.LoginUserBO;
import com.sudoku.security.service.UserTokenService;
import com.sudoku.utils.SecurityUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * token认证过滤器 处理token
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private UserTokenService tokenService;

  /**
   * 过滤处理
   *
   * @param request  请求对象
   * @param response 响应对象
   * @param chain    过滤链
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    LoginUserBO loginUserBO = tokenService.getLoginUser(request);
    if (loginUserBO != null && SecurityUtils.getAuthentication() == null) {
      tokenService.verifyToken(loginUserBO);
      setAuthenticationToContext(request, loginUserBO);
    }
    chain.doFilter(request, response);
  }

  /**
   * 设置认证对象到Security上下文中
   *
   * @param request   请求对象
   * @param loginUserBO 登录用户对象
   */
  private void setAuthenticationToContext(HttpServletRequest request, LoginUserBO loginUserBO) {
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUserBO, null,
        loginUserBO.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
