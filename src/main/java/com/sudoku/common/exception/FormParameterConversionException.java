package com.sudoku.common.exception;

import com.sudoku.common.constant.enums.StatusCode;
import lombok.Getter;

/**
 * 表单参数转换异常类
 */
@Getter
public class FormParameterConversionException extends BaseException {

  private static final long serialVersionUID = 1166533932002631826L;
  private String convertString;
  private Class<?> targetClass;

  /**
   * 表单参数转换异常类的无参构造方法
   */
  public FormParameterConversionException() {
    super(StatusCode.FORM_PARAMETER_CONVERSION_ERROR);
  }

  /**
   * 表单参数转换异常类的构造方法
   *
   * @param convertString 进行转换的字符串
   * @param targetClass   转换的Class对象
   */
  public FormParameterConversionException(String convertString, Class<?> targetClass) {
    super(StatusCode.FORM_PARAMETER_CONVERSION_ERROR);
    this.convertString = convertString;
    this.targetClass = targetClass;
  }

  /**
   * 表单参数转换异常类的构造方法
   *
   * @param statusCode 状态编码
   */
  public FormParameterConversionException(StatusCode statusCode) {
    super(statusCode);
  }
}