package com.sudoku.exception;

import com.sudoku.constant.enums.StatusCode;
import java.util.function.Supplier;
import lombok.Getter;

/**
 * 用户异常类
 */
@Getter
public class UserException extends BaseException {

  private static final long serialVersionUID = -799932046646436382L;

  /**
   * 用户异常类的无参构造方法
   */
  public UserException() {
    super(StatusCode.USER_ERROR);
  }

  /**
   * 用户异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public UserException(StatusCode statusCode) {
    super(statusCode);
  }

}
