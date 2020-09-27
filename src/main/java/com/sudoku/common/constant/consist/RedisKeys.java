package com.sudoku.common.constant.consist;

/**
 * Redis的key值
 */
public class RedisKeys {

  /**
   * 验证码key值的前缀
   */
  public static final String CAPTCHA_PREFIX = "captcha:";
  /**
   * 令牌key值的前缀
   */
  public static final String TOKEN_PREFIX = "token:";
  /**
   * 登录用户key值的前缀
   */
  public static final String LOGIN_USER_PREFIX = "login_user:";
  /**
   * 游戏记录key值的前缀
   */
  public static final String GAME_RECORD_PREFIX = "game_record:";
  /**
   * 用户总数的key值
   */
  public static final String USER_TOTAL = "user_total";
  /**
   * 游戏总局数的key值
   */
  public static final String GAME_TOTAL = "game_total";

  private RedisKeys() {
  }
}
