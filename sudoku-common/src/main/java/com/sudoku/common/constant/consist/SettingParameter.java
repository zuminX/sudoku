package com.sudoku.common.constant.consist;

import java.time.Duration;

/**
 * 系统设定的参数
 */
public class SettingParameter {

  /**
   * 排行榜人数
   */
  public static final Integer RANKING_NUMBER = 10_000;

  /**
   * 比赛的最短时间
   */
  public static final Duration MINIMUM_RACE_DURATION = Duration.ofMinutes(5);

  /**
   * 私有构造方法，防止实例化
   */
  private SettingParameter() {
  }
}
