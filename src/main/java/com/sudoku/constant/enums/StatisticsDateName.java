package com.sudoku.constant.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统计日期的名字
 */
@AllArgsConstructor
@Getter
public enum StatisticsDateName {
  DAILY("日"),
  EACH_MONTH("月"),
  EACH_QUARTER("季度"),
  EACH_YEAR("年");

  private final String name;

  public static StatisticsDateName findByName(String name) {
    return Arrays.stream(StatisticsDateName.values())
        .filter(statisticsDateName -> statisticsDateName.getName().equals(name))
        .findFirst()
        .orElse(null);
  }
}
