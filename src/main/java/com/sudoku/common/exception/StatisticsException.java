package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import lombok.Getter;

/**
 * 统计异常类
 */
@Getter
public class StatisticsException extends BaseException {

  private static final long serialVersionUID = 5955311444611029160L;

  /**
   * 统计异常类的无参构造方法
   */
  public StatisticsException() {
    super(StatusCode.CAPTCHA_ERROR);
  }

  /**
   * 统计异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public StatisticsException(StatusCode statusCode) {
    super(statusCode);
  }
}
