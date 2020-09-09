package com.sudoku.security.handler;

import com.sudoku.security.model.LoginUserBO;
import com.sudoku.security.service.UserTokenService;
import com.sudoku.utils.ServletUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 自定义登出处理器
 */
@Configuration
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

  @Autowired
  private UserTokenService tokenService;

  /**
   * 登出成功处理
   *
   * @param request        请求对象
   * @param response       响应对象
   * @param authentication 认证对象
   */
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    LoginUserBO loginUserBO = tokenService.getLoginUser(request);
    if (loginUserBO != null) {
      tokenService.deleteLoginUser(loginUserBO.getUuid());
    }
    ServletUtils.returnHome(request, response);
  }
}
