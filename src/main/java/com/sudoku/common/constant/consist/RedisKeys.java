package com.sudoku.common.constant.consist;

/**
 * Redis的Key值类
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
   * 平均花费时间排行key值的前缀
   */
  public static final String AVERAGE_SPEND_TIME_RANKING_PREFIX = "average_spend_time_ranking:";

  /**
   * 最少花费时间排行key值的前缀
   */
  public static final String MIN_SPEND_TIME_RANKING_PREFIX = "min_spend_time_ranking:";

  /**
   * 回答正确数排行key值的前缀
   */
  public static final String CORRECT_NUMBER_RANKING_PREFIX = "correct_number_ranking:";

  /**
   * 用户总数的key值
   */
  public static final String USER_TOTAL = "user_total";

  /**
   * 游戏总局数的key值
   */
  public static final String GAME_TOTAL = "game_total";

  /**
   * 公开游戏竞赛的key值
   */
  public static final String PUBLIC_RACE = "public_race";

  /**
   * 私有构造方法，防止实例化
   */
  private RedisKeys() {
  }
}
