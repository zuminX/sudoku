package com.sudoku.common.constant.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 统计日期类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticsDateTest {

  /**
   * 统计日期名为null的查询结果应是null
   */
  @Test
  void testFindByNameWithNull() {
  }

  /**
   * 统计日期名为空时，查询结果应是null
   */
  @Test
  void testFindByNameWithEmpty() {
    assertNull(StatisticsDate.findByName(""));
    assertNull(StatisticsDate.findByName(null));
  }

  /**
   * 测试根据名字查找对应的统计日期对象
   */
  @Test
  void testFindByName() {
    StatisticsDate eachMonthStatisticsDate = StatisticsDate.EACH_MONTH;
    StatisticsDate findStatisticsDate = StatisticsDate.findByName(eachMonthStatisticsDate.getName());
    assertEquals(eachMonthStatisticsDate, findStatisticsDate);
  }

  /**
   * 测试统计日期为日的增加天数的方法
   */
  @Test
  void testPlusByDaily() {
    LocalDate standardLocalDate = LocalDate.of(2000, 1, 1);
    LocalDate expectedLocalDate = LocalDate.of(2000, 1, 2);
    assertEquals(expectedLocalDate, StatisticsDate.DAILY.plus(standardLocalDate, 1L));
  }

  /**
   * 测试统计日期为月的增加月数的方法
   */
  @Test
  void testPlusByEachMonth() {
    LocalDate standardLocalDate = LocalDate.of(2000, 1, 1);
    LocalDate expectedLocalDate = LocalDate.of(2000, 2, 1);
    assertEquals(expectedLocalDate, StatisticsDate.EACH_MONTH.plus(standardLocalDate, 1L));
  }

  /**
   * 测试统计日期为季度的增加季度数的方法
   */
  @Test
  void testPlusByEachQuarter() {
    LocalDate standardLocalDate = LocalDate.of(2000, 1, 1);
    LocalDate expectedLocalDate = LocalDate.of(2000, 5, 1);
    assertEquals(expectedLocalDate, StatisticsDate.EACH_QUARTER.plus(standardLocalDate, 1L));
  }

  /**
   * 测试统计日期为年的增加年数的方法
   */
  @Test
  void testPlusByEachYear() {
    LocalDate standardLocalDate = LocalDate.of(2000, 1, 1);
    LocalDate expectedLocalDate = LocalDate.of(2001, 1, 1);
    assertEquals(expectedLocalDate, StatisticsDate.EACH_YEAR.plus(standardLocalDate, 1L));
  }
}