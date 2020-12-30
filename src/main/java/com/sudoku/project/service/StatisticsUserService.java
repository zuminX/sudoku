package com.sudoku.project.service;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import java.time.LocalDate;
import java.util.ArrayList;
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
    LocalDate nowDate = date.getFirst(startDate), lastDate = date.getFirst(endDate);
    List<StatisticsUserDataBO> statisticsUserDataList = new ArrayList<>();
    while (nowDate.compareTo(lastDate) < 0) {
      LocalDate nextDate = date.next(nowDate);
      Integer newUserTotal = userMapper.countNewUserByDateBetween(nowDate, nextDate);
      Integer recentLoginUserTotal = userMapper.countRecentLoginUserByDateBetween(nowDate, nextDate);
      statisticsUserDataList.add(new StatisticsUserDataBO(newUserTotal, recentLoginUserTotal));
      nowDate = nextDate;
    }
    return statisticsUserDataList;
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
