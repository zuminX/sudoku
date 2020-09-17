package com.sudoku.handler;

import static com.sudoku.constant.enums.StatusCode.ERROR;
import static com.sudoku.constant.enums.StatusCode.INVALID_REQUEST_PARAM_ERROR;

import cn.hutool.core.util.StrUtil;
import com.sudoku.exception.BaseException;
import com.sudoku.exception.CaptchaException;
import com.sudoku.exception.LoginException;
import com.sudoku.exception.UserException;
import com.sudoku.model.vo.CommonResult;
import java.io.Serializable;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    log.error("[所有异常处理]", e);
    return CommonResult.error(ERROR);
  }

  /**
   * 处理所有的异常类
   *
   * @param e 异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BaseException.class)
  public CommonResult<BaseException> baseExceptionHandler(BaseException e) {
    return simpleExceptionProcess("[基础异常]", e);
  }

  /**
   * 处理用户异常类
   *
   * @param e 用户异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = UserException.class)
  public CommonResult<UserException> userExceptionHandler(UserException e) {
    return simpleExceptionProcess("[用户异常]", e);
  }

  /**
   * 处理登录异常类
   *
   * @param e 登录异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = LoginException.class)
  public CommonResult<LoginException> loginExceptionHandler(LoginException e) {
    return simpleExceptionProcess("[登录异常]", e);
  }

  /**
   * 处理验证码异常类
   *
   * @param e 登录异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = CaptchaException.class)
  public CommonResult<CaptchaException> captchaExceptionHandler(CaptchaException e) {
    return simpleExceptionProcess("[验证码异常]", e);
  }

  /**
   * 处理参数校验异常类
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public CommonResult<ConstraintViolationException> constraintViolationExceptionHandler(ConstraintViolationException e) {
    log.debug("[参数校验异常]", e);
    return buildInvalidParamErrorCommonResult(
        e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";")));
  }

  /**
   * 处理参数校验异常类
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = BindException.class)
  public CommonResult<java.net.BindException> bindExceptionHandler(BindException e) {
    log.debug("[参数校验异常]", e);
    return buildInvalidParamErrorCommonResult(
        e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";")));
  }

  /**
   * 处理参数校验异常类
   *
   * @param e 参数校验异常
   * @return 经过包装的结果对象
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public CommonResult<MethodArgumentNotValidException> bindExceptionHandler(MethodArgumentNotValidException e) {
    log.debug("[参数校验异常]", e);
    return buildInvalidParamErrorCommonResult(
        e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(
            Collectors.joining(";")));
  }

  /**
   * 简易异常处理
   *
   * @param header    日志头
   * @param exception 异常
   * @param <T>       异常类型
   * @return 经过包装的结果对象
   */
  private <T extends BaseException> CommonResult<T> simpleExceptionProcess(String header, T exception) {
    log.debug(header, exception);
    return CommonResult.error(exception.getStatusCode());
  }

  /**
   * 构建无效的参数的返回结果
   *
   * @param error 异常原因
   * @param <T>   异常类型
   * @return 经过包装的结果对象
   */
  private <T extends Serializable> CommonResult<T> buildInvalidParamErrorCommonResult(String error) {
    String tip = null;
    if (StrUtil.isNotBlank(error)) {
      tip = StrUtil.format(INVALID_PARAM_TIP_TEMPLATE, INVALID_REQUEST_PARAM_ERROR.getMessage(), error);
    }
    return CommonResult.error(INVALID_REQUEST_PARAM_ERROR, tip);
  }
}
