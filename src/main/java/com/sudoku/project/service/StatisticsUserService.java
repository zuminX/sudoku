package com.sudoku.project.service;

import com.sudoku.common.constant.enums.StatisticsDateName;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 统计用户的业务层的接口
 */
public interface StatisticsUserService {

  /**
   * 获取用户统计信息列表
   *
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param dateName  统计日期的名字
   * @return 用户统计信息列表
   */
  List<StatisticsUserDataBO> getStatisticsUserData(LocalDateTime startDate, LocalDateTime endDate, StatisticsDateName dateName);

  /**
   * 更新每日的统计信息，直到当前
   */
  void updateDailyDataUntilNow();

  /**
   * 更新每月的统计信息，直到当前
   */
  void updateEachMonthDataUntilNow();
}
