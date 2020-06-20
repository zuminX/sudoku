package com.sudoku.handler;

import static com.sudoku.constant.enums.StatusCode.ERROR;
import static com.sudoku.constant.enums.StatusCode.INVALID_REQUEST_PARAM_ERROR;

import cn.hutool.core.util.StrUtil;
import com.sudoku.exception.LoginException;
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

  private static final String INVALID_PARAM_TIP_TEMPLATE = "{}：{}";

  /**
   * 处理所有的异常类
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = Exception.class)
  public CommonResult<Exception> exceptionHandler(Exception e) {
    //记录异常日志
    log.debug("[所有异常处理]", e);
    return CommonResult.error(ERROR);
  }

  /**
   * 处理用户异常类
   *
   * @param e 用户异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = UserException.class)
  public CommonResult<UserException> userExceptionHandler(UserException e) {
    log.debug("[用户异常处理]", e);
    return CommonResult.error(e.getStatusCode());
  }

  /**
   * 处理登录异常类
   *
   * @param e 登录异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = LoginException.class)
  public CommonResult<LoginException> loginExceptionHandler(LoginException e) {
    log.debug("[登录异常处理]", e);
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
    log.debug("[参数校验异常处理]", e);
    String error = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
    return buildInvalidParamErrorCommonResult(error);
  }

  /**
   * 处理参数校验异常类
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BindException.class)
  public CommonResult<BindException> bindExceptionHandler(BindException e) {
    log.debug("[参数校验异常处理]", e);
    String error = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
    return buildInvalidParamErrorCommonResult(error);
  }

  /**
   * 构建无效的参数的返回结果
   *
   * @param error 异常原因
   * @param <T>   异常类型
   * @return 经过包装的结果对象
   */
  private <T> CommonResult<T> buildInvalidParamErrorCommonResult(String error) {
    String tip = null;
    if (StrUtil.isNotBlank(error)) {
      tip = StrUtil.format(INVALID_PARAM_TIP_TEMPLATE, INVALID_REQUEST_PARAM_ERROR.getMessage(), error);
    }
    return CommonResult.error(INVALID_REQUEST_PARAM_ERROR, tip);
  }
}
