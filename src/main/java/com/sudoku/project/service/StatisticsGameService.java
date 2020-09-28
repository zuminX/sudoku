package com.sudoku.project.service;

import com.sudoku.common.constant.enums.StatisticsDate;
import java.time.LocalDate;
import java.util.List;

public interface StatisticsGameService {

  /**
   * 获取在[startDate,endDate)中的游戏局数
   *
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param date      统计日期
   * @return 游戏局数
   */
  List<Integer> getGameTotal(LocalDate startDate, LocalDate endDate, StatisticsDate date);

  /**
   * 获取系统游戏总局数
   *
   * @return 游戏总局数
   */
  Integer getGameTotal();

  void updateDailyDataUntilNow();

  void updateEachMonthDataUntilNow();
}
