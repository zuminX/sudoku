package com.sudoku.framework.security.service.impl;

import com.sudoku.common.constant.enums.StatisticsDateName;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.StatisticsException;
import com.sudoku.project.mapper.StatisticsUserMapper;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.bo.StatisticsUserDataBO;
import com.sudoku.project.model.entity.StatisticsUser;
import com.sudoku.framework.security.service.StatisticsUserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计用户的业务层实现类
 */
@Service
public class StatisticsUserServiceImpl implements StatisticsUserService {

  @Autowired
  private StatisticsUserMapper statisticsUserMapper;
  @Autowired
  private UserMapper userMapper;

  /**
   * 获取用户统计信息列表
   *
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param dateName  统计日期的名字
   * @return 用户统计信息列表
   */
  @Override
  public List<StatisticsUserDataBO> getStatisticsUserData(LocalDateTime startDate, LocalDateTime endDate, StatisticsDateName dateName) {
    if (startDate.compareTo(endDate) > 0) {
      throw new StatisticsException(StatusCode.STATISTICS_INQUIRY_DATE_INVALID);
    }
    return statisticsUserMapper.selectUserTotalAndUserActiveTotalByDateBetweenAndDateName(startDate, endDate, dateName.getName());
  }

  /**
   * 更新每日的统计信息，直到当前
   */
  @Override
  @Transactional
  public void updateDailyDataUntilNow() {
    new UpdateDataTemplateMethod(StatisticsDateName.DAILY, LocalDateTime::plusDays) {
      @Override
      public StatisticsUserDataBO getStatisticsUserData(LocalDateTime startDate, LocalDateTime endDate) {
        Integer userTotal = userMapper.countByCreateTimeBetween(startDate, endDate);
        Integer userActiveTotal = userMapper.countByRecentLoginTimeBetween(startDate, endDate);
        return new StatisticsUserDataBO(userTotal, userActiveTotal);
      }
    }.updateData();
  }

  /**
   * 更新每月的统计信息，直到当前
   */
  @Override
  @Transactional
  public void updateEachMonthDataUntilNow() {
    new UpdateDataTemplateMethod(StatisticsDateName.EACH_MONTH, LocalDateTime::plusMonths) {
      @Override
      public StatisticsUserDataBO getStatisticsUserData(LocalDateTime startDate, LocalDateTime endDate) {
        //获取该月的用户统计数据列表
        List<StatisticsUserDataBO> statisticsUserDataList = statisticsUserMapper.selectUserTotalAndUserActiveTotalByDateBetweenAndDateName(
            startDate, endDate, StatisticsDateName.EACH_MONTH.getName());
        Integer userTotal = statisticsUserDataList.stream().mapToInt(StatisticsUserDataBO::getUserTotal).sum();
        Integer userActiveTotal = statisticsUserDataList.stream().mapToInt(StatisticsUserDataBO::getUserActiveTotal).sum();
        return new StatisticsUserDataBO(userTotal, userActiveTotal);
      }
    }.updateData();
  }

  /**
   * 更新统计数据的模板方法
   */
  private abstract class UpdateDataTemplateMethod {

    private final String statisticsDateName;
    private final BiFunction<LocalDateTime, Long, LocalDateTime> plusDateFunction;

    public UpdateDataTemplateMethod(StatisticsDateName statisticsDateName,
        BiFunction<LocalDateTime, Long, LocalDateTime> plusDateFunction) {
      this.statisticsDateName = statisticsDateName.getName();
      this.plusDateFunction = plusDateFunction;
    }

    /**
     * 获取该日期的下一个日期
     *
     * @param date 日期
     * @return 给定日期的下一个日期
     */
    private LocalDateTime getNextDate(LocalDateTime date) {
      return plusDateFunction.apply(date, 1L);
    }

    /**
     * 更新统计数据
     */
    public void updateData() {
      //从最新统计的日期后一个统计日期开始
      LocalDateTime startDate = getNextDate(statisticsUserMapper.findFirstDateByDateNameOrderByDateDesc(statisticsDateName));
      LocalDateTime nowDate = LocalDateTime.now();
      LocalDateTime endDate = getNextDate(startDate);
      //更新数据，直到统计的时间超过当前时间
      while (endDate.compareTo(nowDate) < 0) {
        StatisticsUserDataBO statisticsUserData = getStatisticsUserData(startDate, endDate);
        statisticsUserMapper.insert(StatisticsUser.builder()
            .userTotal(statisticsUserData.getUserTotal())
            .userActiveTotal(statisticsUserData.getUserActiveTotal())
            .dateName(statisticsDateName)
            .date(startDate)
            .build());
        startDate = getNextDate(startDate);
        endDate = getNextDate(startDate);
      }
    }

    /**
     * 获取待插入数据库的用户统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 用户的统计数据
     */
    public abstract StatisticsUserDataBO getStatisticsUserData(LocalDateTime startDate, LocalDateTime endDate);
  }

}
