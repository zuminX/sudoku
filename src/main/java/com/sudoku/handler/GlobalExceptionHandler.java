package com.sudoku.handler;

import com.alibaba.druid.util.StringUtils;
import com.sudoku.constant.enums.StatusCode;
import com.sudoku.exception.UserException;
import com.sudoku.model.vo.CommonResult;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 处理所有的异常类
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = Exception.class)
  public CommonResult<Exception> exceptionHandler(Exception e) {
    //记录异常日志
    log.debug("[exceptionHandler]", e);
    return CommonResult.error(StatusCode.ERROR);
  }

  /**
   * 处理用户异常类
   *
   * @param e 用户异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = UserException.class)
  public CommonResult<UserException> userExceptionHandler(UserException e) {
    log.debug("[userExceptionHandler]", e);
    return CommonResult.error(e.getStatusCode());
  }

  /**
   * 处理参数校验异常类
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public CommonResult<ConstraintViolationException> constraintViolationExceptionHandler(ConstraintViolationException e) {
    log.debug("[constraintViolationExceptionHandler]", e);
    //拼接错误
    String sb = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
    return buildInvalidParamErrorCommonResult(sb);
  }

  /**
   * 处理参数校验异常类
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BindException.class)
  public CommonResult<BindException> bindExceptionHandler(BindException e) {
    log.debug("[bindExceptionHandler]", e);
    //拼接错误
    String sb = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
    return buildInvalidParamErrorCommonResult(sb);
  }

  /**
   * 构建无效的参数的返回结果
   *
   * @param sb
   * @param <T>
   * @return
   */
  private <T> CommonResult<T> buildInvalidParamErrorCommonResult(String sb) {
    //若错误为空，则使用默认异常信息
    if (StringUtils.isEmpty(sb)) {
      return CommonResult.error(StatusCode.INVALID_REQUEST_PARAM_ERROR);
      //否则，添加上错误原因
    } else {
      return CommonResult.error(StatusCode.INVALID_REQUEST_PARAM_ERROR,
          String.format("%s：%s", StatusCode.INVALID_REQUEST_PARAM_ERROR.getMessage(), sb));
    }
  }
}
