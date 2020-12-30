package com.sudoku.common.constant.enums;

import com.sudoku.common.tools.LocalDateOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@ToString
@ApiModel("统计日期类")
public enum StatisticsDate implements LocalDateOperation {
  DAILY("日") {
    /**
     * 返回日期加上指定天的副本
     * @param date 日期
     * @param days 天数
     * @return 给定日期增加指定天数后的日期
     */
    @Override
    public LocalDate plus(@NotNull LocalDate date, long days) {
      return date.plusDays(days);
    }

    @Override
    public LocalDate getFirst(@NotNull LocalDate date) {
      return date;
    }

    @Override
    public LocalDate getLast(@NotNull LocalDate date) {
      return date;
    }
  },
  EACH_MONTH("月") {
    /**
     * 返回日期加上指定月的副本
     * @param date 日期
     * @param months 月数
     * @return 给定日期增加指定月数后的日期
     */
    @Override
    public LocalDate plus(@NotNull LocalDate date, long months) {
      return date.plusMonths(months);
    }

    @Override
    public LocalDate getFirst(@NotNull LocalDate date) {
      return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    @Override
    public LocalDate getLast(@NotNull LocalDate date) {
      return date.with(TemporalAdjusters.lastDayOfMonth());
    }
  },
  EACH_QUARTER("季度") {
    /**
     * 返回日期加上指定季度的副本
     * @param date 日期
     * @param quarters 季度数
     * @return 给定日期增加指定季度数后的日期
     */
    @Override
    public LocalDate plus(@NotNull LocalDate date, long quarters) {
      return date.plusMonths(4 * quarters);
    }

    @Override
    public LocalDate getFirst(@NotNull LocalDate date) {
      Month firstMonthOfQuarter = date.getMonth().firstMonthOfQuarter();
      return LocalDate.of(date.getYear(), firstMonthOfQuarter, 1);
    }

    @Override
    public LocalDate getLast(@NotNull LocalDate date) {
      Month firstMonthOfQuarter = date.getMonth().firstMonthOfQuarter();
      Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
      return LocalDate.of(date.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(date.isLeapYear()));
    }
  },
  EACH_YEAR("年") {
    /**
     * 返回日期加上指定年的副本
     * @param date 日期
     * @param years 年数
     * @return 给定日期增加指定年数后的日期
     */
    @Override
    public LocalDate plus(@NotNull LocalDate date, long years) {
      return date.plusYears(years);
    }

    @Override
    public LocalDate getFirst(@NotNull LocalDate date) {
      return date.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 获取该日期的最后一天
     *
     * @param date 日期
     * @return 该日期的最后一天
     */
    @Override
    public LocalDate getLast(@NotNull LocalDate date) {
      return date.with(TemporalAdjusters.lastDayOfYear());
    }
  };

  @ApiModelProperty("统计日期名称")
  private final String name;

  /**
   * 根据名字查找对应的统计日期对象
   *
   * @param name 统计日期名
   * @return 若存在该名字的统计日期对象，则将其返回。否则返回null
   */
  public static StatisticsDate findByName(String name) {
    return Arrays.stream(StatisticsDate.values())
        .filter(statisticsDate -> statisticsDate.getName().equals(name))
        .findFirst()
        .orElse(null);
  }

}
