package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import lombok.Getter;

/**
 * 数独比赛异常类
 */
@Getter
public class RaceException extends BaseException {

  private static final long serialVersionUID = 3794118439430220098L;

  /**
   * 数独异常类的无参构造方法
   */
  public RaceException() {
    super(StatusCode.RACE_ERROR);
  }

  /**
   * 数独异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public RaceException(StatusCode statusCode) {
    super(statusCode);
  }
}
