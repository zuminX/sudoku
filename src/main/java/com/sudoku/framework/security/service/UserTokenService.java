package com.sudoku.framework.security.service;

import com.sudoku.framework.security.model.LoginUserBO;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户令牌业务层接口
 */
public interface UserTokenService {

  /**
   * 获取登录用户
   *
   * @param request 请求对象
   * @return 登录用户对象
   */
  LoginUserBO getLoginUser(HttpServletRequest request);

  /**
   * 创建Token
   *
   * @param loginUserBO 登录用户对象
   * @return 令牌
   */
  String createToken(LoginUserBO loginUserBO);

  /**
   * 刷新Token有效期
   *
   * @param loginUserBO 登录用户对象
   */
  void verifyToken(LoginUserBO loginUserBO);

  /**
   * 删除登录用户信息
   *
   * @param uuid 唯一标识
   */
  void deleteLoginUser(String uuid);
}
