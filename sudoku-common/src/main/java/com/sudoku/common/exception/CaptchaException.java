package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import lombok.Getter;

/**
 * 验证码异常类
 */
@Getter
public class CaptchaException extends BaseException {

  private static final long serialVersionUID = -2676690140146545943L;

  /**
   * 验证码异常类的无参构造方法
   */
  public CaptchaException() {
    super(StatusCode.CAPTCHA_ERROR);
  }

  /**
   * 验证码异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public CaptchaException(StatusCode statusCode) {
    super(statusCode);
  }
}
