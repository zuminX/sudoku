package com.sudoku.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 响应状态编码
 */
@AllArgsConstructor
@Getter
@ToString
public enum StatusCode {
  OK(0, HttpStatus.OK, "成功请求"),
  ERROR(1, HttpStatus.BAD_REQUEST, "服务端发生了未知错误"),

  //请求参数不合法
  INVALID_REQUEST_PARAM_ERROR(2, HttpStatus.FORBIDDEN, "请求参数不合法"),

  //请求异常
  RESOURCE_NOT_FOUND(100, HttpStatus.NOT_FOUND, "未找到指定的资源"),
  REQUEST_VALIDATION_FAILED(101, HttpStatus.BAD_REQUEST, "请求数据格式验证失败"),

  //用户异常
  USER_ERROR(200, HttpStatus.BAD_REQUEST, "服务器在处理用户时发生了错误"),
  USER_NOT_FOUND(201, HttpStatus.NOT_FOUND, "未找到指定的用户"),
  USER_HAS_EQUAL_NAME(202, HttpStatus.BAD_REQUEST, "存在相同用户名的用户"),
  USER_NOT_AUTHORITY(203, HttpStatus.FORBIDDEN, "没有权限访问该地址"),
  USER_REPEAT_PASSWORD_ERROR(204, HttpStatus.BAD_REQUEST, "重复密码与密码不一致"),
  USER_NOT_ENABLED(205, HttpStatus.FORBIDDEN, "该用户处于禁用状态"),

  //登录异常
  LOGIN_ERROR(300, HttpStatus.BAD_REQUEST, "登录时发生错误"),
  LOGIN_NO_STATUS(301, HttpStatus.UNAUTHORIZED, "尚未登陆，请先登录"),
  LOGIN_PASSWORD_ERROR(302, HttpStatus.FORBIDDEN, "用户名和密码不一致"),

  //数独游戏异常
  GAME_ERROR(400, HttpStatus.BAD_REQUEST, "进行数独游戏时发送错误"),
  GAME_NOT_LEVEL(401, HttpStatus.NOT_FOUND, "没有找到对应的数独难度"),

  //验证码异常
  CAPTCHA_ERROR(500, HttpStatus.BAD_REQUEST, "生成验证码时发生错误"),
  CAPTCHA_EXPIRED(501, HttpStatus.BAD_REQUEST, "验证码过期，请重新生成验证码"),
  CAPTCHA_NOT_EQUALS(502, HttpStatus.FORBIDDEN, "验证码错误，请重新生成验证码"),
  ;

  /**
   * 编号
   */
  private final int code;
  /**
   * HTTP状态
   */
  private final HttpStatus status;
  /**
   * 错误信息
   */
  private final String message;
}
