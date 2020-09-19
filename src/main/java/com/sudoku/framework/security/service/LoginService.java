package com.sudoku.framework.security.service;

import com.sudoku.framework.security.model.LoginBody;
import com.sudoku.framework.security.model.LoginSuccessVO;

/**
 * 登录功能业务层接口
 */
public interface LoginService {

  /**
   * 用户登录
   *
   * @param loginBody 用户登录表单类
   * @return 登录生成显示对象
   */
  LoginSuccessVO login(LoginBody loginBody);
}
