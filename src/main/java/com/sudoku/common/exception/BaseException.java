package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基础异常类
 */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

  private static final long serialVersionUID = -8105492018397966401L;
  /**
   * 异常编号
   */
  private final StatusCode statusCode;

}
