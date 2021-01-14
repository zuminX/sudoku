package com.sudoku.project.service;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.project.core.GetStatisticsDataTemplate;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
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
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param date      统计日期
   * @return 用户统计信息列表
   */
  @Cacheable(value = "statisticsUserData", keyGenerator = "simpleKG")
  public List<StatisticsUserDataBO> getStatisticsUserData(LocalDate startDate, LocalDate endDate, StatisticsDate date) {
    return new GetStatisticsDataTemplate<StatisticsUserDataBO>(startDate, endDate, date).getData((firstDate, lastDate) -> {
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
  @Cacheable(value = "userTotal", keyGenerator = "simpleKG")
  public Integer getUserTotal() {
    return userMapper.count();
  }
}
