package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import java.util.function.Supplier;
import lombok.Getter;

/**
 * 数独等级异常类
 */
@Getter
public class SudokuLevelException extends BaseException {

  private static final long serialVersionUID = -5342752752493073486L;

  /**
   * 数独等级异常类的无参构造方法
   */
  public SudokuLevelException() {
    super(StatusCode.SUDOKU_LEVEL_ERROR);
  }

  /**
   * 数独等级异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public SudokuLevelException(StatusCode statusCode) {
    super(statusCode);
  }

  public static Supplier<SudokuLevelException> supplier(StatusCode statusCode) {
    return () -> new SudokuLevelException(statusCode);
  }
}
