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

  private RedisKeys() {
  }
}
