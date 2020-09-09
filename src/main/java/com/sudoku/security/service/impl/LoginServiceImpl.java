package com.sudoku.security.service.impl;

import static com.sudoku.constant.enums.StatusCode.LOGIN_ERROR;
import static com.sudoku.constant.enums.StatusCode.LOGIN_PASSWORD_ERROR;

import com.sudoku.convert.UserConvert;
import com.sudoku.exception.LoginException;
import com.sudoku.security.model.LoginBody;
import com.sudoku.security.model.LoginSuccessVO;
import com.sudoku.security.model.LoginUserBO;
import com.sudoku.security.service.LoginService;
import com.sudoku.security.service.UserTokenService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 登录功能业务层实现类
 */
@Service
public class LoginServiceImpl implements LoginService {

  @Resource
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserTokenService tokenService;

  /**
   * 用户登录
   *
   * @param loginBody 用户登录信息对象
   * @return 登录生成显示对象
   */
  @Override
  public LoginSuccessVO login(LoginBody loginBody) {
    LoginUserBO loginUserBO = checkPassword(loginBody);
    String token = tokenService.createToken(loginUserBO);
    return new LoginSuccessVO(UserConvert.INSTANCE.convert(loginUserBO.getUser()), token);
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
