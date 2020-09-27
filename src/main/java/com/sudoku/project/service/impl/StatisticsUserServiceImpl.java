package com.sudoku.project.service.impl;

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
import com.sudoku.project.service.StatisticsUserService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计用户信息的业务层实现类
 */
@Service
public class StatisticsUserServiceImpl implements StatisticsUserService {

  @Autowired
  private StatisticsUserMapper statisticsUserMapper;
  @Autowired
  private UserMapper userMapper;

  /**
   * 获取在[startDate,endDate)中的用户统计信息列表
   *
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param date      统计日期
   * @return 用户统计信息列表
   */
  @Override
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
  @Override
  public Integer getUserTotal() {
    return new UpdateOutDatedDataInRedis<Integer>(RedisKeys.USER_TOTAL) {
      @Override
      public Integer getLatestDataIfEmpty() {
        return statisticsUserMapper.selectNewUserTotalSumByDateName(StatisticsDate.DAILY.getName());
      }

      @Override
      public Integer getLatestData(DataStamped<Integer> oldData) {
        Integer newUserTotal = statisticsUserMapper.selectNewUserTotalSumByDateAfterAndDateName(oldData.getUpdateDate().plusDays(1L),
            StatisticsDate.DAILY.getName());
        return oldData.getData() + newUserTotal;
      }
    }.updateData();
  }

  /**
   * 更新每日的统计信息，直到当前
   */
  @Override
  public void updateDailyDataUntilNow() {
    new UpdateUserStatisticsData(StatisticsDate.DAILY) {
      @Override
      public StatisticsUserDataBO getStatisticsUserData(LocalDate startDate, LocalDate endDate) {
        Integer newUserTotal = userMapper.countByCreateTimeBetween(startDate, endDate);
        Integer activeUserTotal = userMapper.countByRecentLoginTimeBetween(startDate, endDate);
        return new StatisticsUserDataBO(newUserTotal, activeUserTotal);
      }
    }.updateData();
  }

  /**
   * 更新每月的统计信息，直到当前
   */
  @Override
  public void updateEachMonthDataUntilNow() {
    new UpdateUserStatisticsData(StatisticsDate.EACH_MONTH) {
      @Override
      public StatisticsUserDataBO getStatisticsUserData(LocalDate startDate, LocalDate endDate) {
        //获取该月的用户统计数据列表
        return statisticsUserMapper.selectNewUserTotalSumAndActiveUserTotalSumByDateBetweenAndDateName(startDate, endDate,
            StatisticsDate.DAILY.getName());
      }
    }.updateData();
  }

  /**
   * 更新用户统计数据的模板方法
   */
  private abstract class UpdateUserStatisticsData extends UpdateStatisticsData {

    /**
     * 该类的构造方法
     *
     * @param statisticsDate 统计日期对象
     */
    public UpdateUserStatisticsData(StatisticsDate statisticsDate) {
      super(statisticsDate);
    }

    /**
     * 获取待插入数据库的用户统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 用户的统计数据
     */
    public abstract StatisticsUserDataBO getStatisticsUserData(LocalDate startDate, LocalDate endDate);

    /**
     * 向数据库插入用户统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     */
    @Override
    @Transactional
    protected void insertData(LocalDate startDate, LocalDate endDate) {
      StatisticsUserDataBO statisticsUserData = getStatisticsUserData(startDate, endDate);
      statisticsUserMapper.insert(StatisticsUserConvert.INSTANCE.convert(statisticsUserData, getStatisticsDate().getName(), startDate));
    }

    /**
     * 获取第一次统计数据的日期
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
