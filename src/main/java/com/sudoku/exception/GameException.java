package com.sudoku.exception;

import com.sudoku.constant.enums.StatusCode;

/**
 * 数独游戏异常类
 */
public class GameException extends BaseException {

  private static final long serialVersionUID = 3787697439163954026L;

  /**
   * 数独游戏异常类的无参构造方法
   */
  public GameException() {
    super(StatusCode.GAME_ERROR);
  }

  /**
   * 数独游戏异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public GameException(StatusCode statusCode) {
    super(statusCode);
  }
}
