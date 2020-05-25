package com.sudoku.exception;

import com.sudoku.constant.enums.StatusCode;

/**
 * 登录异常类
 */
public class LoginException extends BaseException {

  private static final long serialVersionUID = -748986206579209694L;

  /**
   * 登录异常类的无参构造方法
   */
  public LoginException() {
    super(StatusCode.LOGIN_ERROR);
  }

  /**
   * 登录异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public LoginException(StatusCode statusCode) {
    super(statusCode);
  }
}
