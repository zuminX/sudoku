package com.sudoku.project.core;

import com.sudoku.common.constant.enums.StatisticsDate;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

/**
 * 更新统计数据的模板方法
 *
 * @param <T> 统计数据的类型
 */
public abstract class UpdateStatisticsData<T> {

  @Getter
  private final StatisticsDate statisticsDate;

  /**
   * 该类的构造方法
   *
   * @param statisticsDate 统计日期对象
   */
  public UpdateStatisticsData(StatisticsDate statisticsDate) {
    this.statisticsDate = statisticsDate;
  }

  /**
   * 向数据库插入统计数据
   *
   * @param startDate 开始日期
   * @param endDate   结束日期
   * @param callback  获取统计数据的回调方法
   */
  @Transactional
  protected abstract void insertData(LocalDate startDate, LocalDate endDate, StatisticsDataCallback<T> callback);

  /**
   * 获取最新的更新日期
   *
   * @return 最新的更新日期
   */
  protected abstract LocalDate getLatestUpdateDate();

  /**
   * 获取第一次统计数据的日期
   *
   * @return 第一次统计数据的日期
   */
  protected abstract LocalDate getFirstStatisticsDate();

  /**
   * 更新统计数据
   *
   * @param callback 获取统计数据的回调方法
   */
  public void updateData(StatisticsDataCallback<T> callback) {
    //从最新统计的日期后一个统计日期开始
    LocalDate startDate = getStartDate();
    LocalDate nowDate = LocalDate.now();
    LocalDate endDate = getNextDate(startDate);
    //插入数据，直到统计的时间在当前时间的前一天
    while (endDate.compareTo(nowDate) <= 0) {
      insertData(startDate, endDate, callback);
      startDate = getNextDate(startDate);
      endDate = getNextDate(startDate);
    }
  }

  /**
   * 获取起始日期
   *
   * @return 起始日期
   */
  private LocalDate getStartDate() {
    LocalDate lastDate = getLatestUpdateDate();
    return lastDate == null ? getFirstStatisticsDate() : getNextDate(lastDate);
  }

  /**
   * 获取该日期的下一个日期
   *
   * @param date 日期
   * @return 给定日期的下一个日期
   */
  private LocalDate getNextDate(LocalDate date) {
    return statisticsDate.plus(date, 1L);
  }

  /**
   * 获取统计数据的回调方法
   *
   * @param <T> 统计数据的类型
   */
  public interface StatisticsDataCallback<T> {

    /**
     * 获取统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 统计数据
     */
    T getStaticsData(LocalDate startDate, LocalDate endDate);
  }

}
