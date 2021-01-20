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
  public static final String SUDOKU_RECORD_PREFIX = "sudoku_record:";

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
   * 公开游戏竞赛列表的key值
   */
  public static final String PUBLIC_RACE_LIST = "public_race_list";

  /**
   * 公开游戏竞赛映射的key值
   */
  public static final String PUBLIC_RACE_MAP = "public_race_map";

  /**
   * 私有构造方法，防止实例化
   */
  private RedisKeys() {
  }
}
