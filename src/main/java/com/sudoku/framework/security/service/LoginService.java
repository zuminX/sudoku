package com.sudoku.framework.security.service;

import static com.sudoku.common.constant.enums.StatusCode.LOGIN_ERROR;
import static com.sudoku.common.constant.enums.StatusCode.LOGIN_PASSWORD_ERROR;

import com.sudoku.common.exception.LoginException;
import com.sudoku.framework.security.model.LoginBody;
import com.sudoku.framework.security.model.LoginSuccessVO;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.project.convert.UserConvert;
import javax.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 登录业务层类
 */
@Service
public class LoginService {

  private final UserTokenService tokenService;

  private final UserConvert userConvert;

  @Resource
  private AuthenticationManager authenticationManager;

  public LoginService(UserTokenService tokenService, UserConvert userConvert) {
    this.tokenService = tokenService;
    this.userConvert = userConvert;
  }

  /**
   * 用户登录
   *
   * @param loginBody 用户登录信息对象
   * @return 登录生成显示对象
   */
  public LoginSuccessVO login(LoginBody loginBody) {
    LoginUserBO loginUserBO = checkPassword(loginBody);
    String token = tokenService.createToken(loginUserBO);
    return new LoginSuccessVO(userConvert.convert(loginUserBO.getUser()), token);
  }

  /**
   * 检查密码是否正确
   *
   * @param loginBody 用户登录信息对象
   * @return 登录用户对象
   */
  private LoginUserBO checkPassword(LoginBody loginBody) {
    Authentication authentication;
    try {
      authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getUsername(),
          loginBody.getPassword()));
    } catch (BadCredentialsException e) {
      throw new LoginException(LOGIN_PASSWORD_ERROR);
    } catch (Exception e) {
      throw new LoginException(LOGIN_ERROR);
    }
    return (LoginUserBO) authentication.getPrincipal();
  }

}
