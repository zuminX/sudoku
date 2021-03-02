package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import lombok.Getter;

/**
 * 分页异常类
 */
@Getter
public class PageException extends BaseException {

  private static final long serialVersionUID = -1202049169478818925L;

  /**
   * 分页异常类的无参构造方法
   */
  public PageException() {
    super(StatusCode.PAGE_ERROR);
  }

  /**
   * 分页异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public PageException(StatusCode statusCode) {
    super(statusCode);
  }

}
