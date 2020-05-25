package com.sudoku.exception;

import com.sudoku.constant.enums.StatusCode;
import lombok.Getter;

/**
 * 全局异常类
 */
@Getter
public class GlobalException extends BaseException {

  private static final long serialVersionUID = 5359687843832059833L;

  /**
   * 全局异常类的无参构造方法
   */
  public GlobalException() {
    super(StatusCode.ERROR);
  }

  /**
   * 全局异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public GlobalException(StatusCode statusCode) {
    super(statusCode);
  }
}
