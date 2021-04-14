package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;

/**
 * 文件异常类
 */
public class FileException extends BaseException {

  private static final long serialVersionUID = -658002836273149602L;

  public FileException() {
    super(StatusCode.FILE_ERROR);
  }

  public FileException(StatusCode statusCode) {
    super(statusCode);
  }

  public FileException(Throwable cause) {
    super(StatusCode.FILE_ERROR, cause);
  }

}
