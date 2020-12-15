package com.sudoku.project.service;

import com.sudoku.common.constant.consist.RedisKeys;
import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.StatisticsException;
import com.sudoku.common.tools.DataStamped;
import com.sudoku.project.convert.StatisticsUserConvert;
import com.sudoku.project.core.UpdateOutDatedDataInRedis;
import com.sudoku.project.core.UpdateStatisticsData;
import com.sudoku.project.mapper.StatisticsUserMapper;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计用户信息的业务层类
 */
@Service
public class StatisticsUserService {

  private final StatisticsUserMapper statisticsUserMapper;

  private final UserMapper userMapper;

  private final StatisticsUserConvert statisticsUserConvert;

  public StatisticsUserService(StatisticsUserMapper statisticsUserMapper,
      UserMapper userMapper, StatisticsUserConvert statisticsUserConvert) {
    this.statisticsUserMapper = statisticsUserMapper;
    this.userMapper = userMapper;
    this.statisticsUserConvert = statisticsUserConvert;
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
    if (startDate.compareTo(endDate) > 0) {
      throw new StatisticsException(StatusCode.STATISTICS_INQUIRY_DATE_INVALID);
    }
    return statisticsUserMapper.selectNewUserTotalAndActiveUserTotalByDateBetweenAndDateName(startDate, endDate, date.getName());
  }

  /**
   * 获取用户总数
   *
   * @return 用户总数
   */
  public Integer getUserTotal() {
    UpdateOutDatedDataInRedis<Integer> operate = new UpdateOutDatedDataInRedis<>(RedisKeys.USER_TOTAL);
    return operate.updateData((DataStamped<Integer> oldData) -> {
      Integer newUserTotal = statisticsUserMapper.selectNewUserTotalSumByDateAfterAndDateName(oldData.getUpdateDate().plusDays(1L),
          StatisticsDate.DAILY.getName()).orElse(0);
      return oldData.getData() + newUserTotal;
    }, () -> statisticsUserMapper.selectNewUserTotalSumByDateName(StatisticsDate.DAILY.getName()).orElse(0));
  }

  /**
   * 更新每日的统计信息，直到当前
   */
  public void updateDailyDataUntilNow() {
    UpdateUserStatisticsData updateUserStatisticsData = new UpdateUserStatisticsData(StatisticsDate.DAILY);
    updateUserStatisticsData.updateData((LocalDate startDate, LocalDate endDate) -> {
      Integer newUserTotal = userMapper.countByCreateTimeBetween(startDate, endDate).orElse(0);
      Integer activeUserTotal = userMapper.countByRecentLoginTimeBetween(startDate, endDate).orElse(0);
      return new StatisticsUserDataBO(newUserTotal, activeUserTotal);
    });
  }

  /**
   * 更新每月的统计信息，直到当前
   */
  public void updateEachMonthDataUntilNow() {
    UpdateUserStatisticsData updateUserStatisticsData = new UpdateUserStatisticsData(StatisticsDate.EACH_MONTH);
    updateUserStatisticsData.updateData(
        (LocalDate startDate, LocalDate endDate) -> statisticsUserMapper.selectNewUserTotalSumAndActiveUserTotalSumByDateBetweenAndDateName(
            startDate, endDate, StatisticsDate.DAILY.getName()));
  }

  /**
   * 更新用户统计数据类
   */
  private class UpdateUserStatisticsData extends UpdateStatisticsData<StatisticsUserDataBO> {

    /**
     * 该类的构造方法
     *
     * @param statisticsDate 统计日期对象
     */
    public UpdateUserStatisticsData(StatisticsDate statisticsDate) {
      super(statisticsDate);
    }

    /**
     * 向数据库插入用户统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param callback  获取用户的统计数据的回调方法
     */
    @Override
    @Transactional
    protected void insertData(LocalDate startDate, LocalDate endDate, StatisticsDataCallback<StatisticsUserDataBO> callback) {
      StatisticsUserDataBO statisticsUserData = callback.getStaticsData(startDate, endDate);
      if (statisticsUserData == null) {
        statisticsUserData = StatisticsUserDataBO.getZero();
      }
      statisticsUserMapper.insert(statisticsUserConvert.convert(statisticsUserData, getStatisticsDate().getName(), startDate));
    }

    /**
     * 获取最新统计数据的日期
     *
     * @return 第一次统计数据的日期
     */
    @Override
    protected LocalDate getLatestUpdateDate() {
      return statisticsUserMapper.findFirstDateByDateNameOrderByDateDesc(getStatisticsDate().getName());
    }

    /**
     * 使用系统用户中最早注册的日期
     *
     * @return 用户注册的最早日期
     */
    @Override
    protected LocalDate getFirstStatisticsDate() {
      return userMapper.findFirstCreateTimeOrderByCreateTime().toLocalDate();
    }
  }

}
