package com.sudoku.system.service;

import com.sudoku.common.annotation.ExtCacheable;
import com.sudoku.common.core.domain.StatisticsDateRange;
import com.sudoku.common.core.template.GetStatisticsDataTemplate;
import com.sudoku.system.mapper.UserMapper;
import com.sudoku.system.model.bo.StatisticsUserDataBO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 统计用户信息的业务层类
 */
@Service
public class StatisticsUserService {

  private final UserMapper userMapper;

  public StatisticsUserService(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  /**
   * 获取在[startDate,endDate)中的用户统计信息列表
   *
   * @param dateRange 统计日期范围
   * @return 用户统计信息列表
   */
  @ExtCacheable(value = "statisticsUserData", ttlOfDays = 1)
  public List<StatisticsUserDataBO> getStatisticsUserData(StatisticsDateRange dateRange) {
    return new GetStatisticsDataTemplate<StatisticsUserDataBO>(dateRange).getData((firstDate, lastDate) -> {
      Integer newUserTotal = userMapper.countNewUserByDateBetween(firstDate, lastDate);
      Integer recentLoginUserTotal = userMapper.countRecentLoginUserByDateBetween(firstDate, lastDate);
      return new StatisticsUserDataBO(newUserTotal, recentLoginUserTotal);
    });
  }

  /**
   * 获取用户总数
   *
   * @return 用户总数
   */
  @ExtCacheable(value = "userTotal", ttlOfDays = 1)
  public Integer getUserTotal() {
    return userMapper.count();
  }
}
