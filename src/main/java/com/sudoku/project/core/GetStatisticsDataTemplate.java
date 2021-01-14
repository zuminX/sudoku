package com.sudoku.project.core;

import com.sudoku.common.constant.enums.StatisticsDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取统计数据的模板方法
 *
 * @param <T> 统计数据的类型
 */
public class GetStatisticsDataTemplate<T> {

  private final StatisticsDate statisticsDate;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * 该类的构造方法
   *
   * @param startDate      开始日期
   * @param endDate        结束日期
   * @param statisticsDate 统计日期
   */
  public GetStatisticsDataTemplate(LocalDate startDate, LocalDate endDate, StatisticsDate statisticsDate) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.statisticsDate = statisticsDate;
  }

  /**
   * 获取统计数据
   *
   * @param callback 获取统计数据的回调方法
   * @return 统计数据列表
   */
  public List<T> getData(StatisticsDataCallback<T> callback) {
    LocalDate nowDate = statisticsDate.getFirst(startDate), lastDate = statisticsDate.getFirst(endDate);
    List<T> list = new ArrayList<>();
    while (nowDate.compareTo(lastDate) < 0) {
      LocalDate nextDate = statisticsDate.next(nowDate);
      list.add(callback.getData(nowDate, nextDate));
      nowDate = nextDate;
    }
    return list;
  }

  /**
   * 获取统计数据的回调方法
   *
   * @param <T> 统计数据的类型
   */
  public interface StatisticsDataCallback<T> {

    /**
     * 获取[startDate,endDate)中的统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 统计数据
     */
    T getData(LocalDate startDate, LocalDate endDate);
  }

}