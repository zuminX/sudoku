package com.sudoku.common.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.sudoku.common.tools.DateTimeRange;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * 时间日期范围类的验证器类的测试类
 */
@RunWith(PowerMockRunner.class)
public class DateTimeRangeValidatorTest {

  private DateTimeRangeValidator dateTimeRangeValidator;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    dateTimeRangeValidator = new DateTimeRangeValidator();
  }

  /**
   * 测试日期时间范围为null时的校验结果
   */
  @Test
  public void testIsValidWithNull() {
    assertFalse(dateTimeRangeValidator.isValid(null, null));
  }

  /**
   * 测试开始时间和结束时间都能为null时的校验结果
   */
  @Test
  public void testIsValidIfStartAndEndCanNull() {
    DateTimeRange nullStartTimeRange = new DateTimeRange(null, LocalDateTime.MAX);
    DateTimeRange nullEndTimeRange = new DateTimeRange(LocalDateTime.MIN, null);
    DateTimeRange nullStartAndEndTimeRange = new DateTimeRange(null, null);

    assertTrue(dateTimeRangeValidator.isValid(nullStartTimeRange, null));
    assertTrue(dateTimeRangeValidator.isValid(nullEndTimeRange, null));
    assertTrue(dateTimeRangeValidator.isValid(nullStartAndEndTimeRange, null));
  }

  /**
   * 测试开始时间不能为null、结束时间能为null时的校验结果
   */
  @Test
  public void testIsValidIfStartNotNull() {
    Whitebox.setInternalState(dateTimeRangeValidator, "startTimeNotNull", true);

    DateTimeRange nullStartTimeRange = new DateTimeRange(null, LocalDateTime.MAX);
    DateTimeRange nullEndTimeRange = new DateTimeRange(LocalDateTime.MIN, null);

    assertFalse(dateTimeRangeValidator.isValid(nullStartTimeRange, null));
    assertTrue(dateTimeRangeValidator.isValid(nullEndTimeRange, null));
  }

  /**
   * 测试开始时间能为null、结束时间不能为null时的校验结果
   */
  @Test
  public void testIsValidIfEndNotNull() {
    Whitebox.setInternalState(dateTimeRangeValidator, "endTimeNotNull", true);

    DateTimeRange nullStartTimeRange = new DateTimeRange(null, LocalDateTime.MAX);
    DateTimeRange nullEndTimeRange = new DateTimeRange(LocalDateTime.MIN, null);

    assertTrue(dateTimeRangeValidator.isValid(nullStartTimeRange, null));
    assertFalse(dateTimeRangeValidator.isValid(nullEndTimeRange, null));
  }

  /**
   * 测试开始时间和结束时间都不能为null时的校验结果
   */
  @Test
  public void testIsValidIfStartAndEndNotNull() {
    Whitebox.setInternalState(dateTimeRangeValidator, "startTimeNotNull", true);
    Whitebox.setInternalState(dateTimeRangeValidator, "endTimeNotNull", true);

    DateTimeRange nullStartTimeRange = new DateTimeRange(null, LocalDateTime.MAX);
    DateTimeRange nullEndTimeRange = new DateTimeRange(LocalDateTime.MIN, null);
    DateTimeRange startTimeLessEndTimeRange = new DateTimeRange(LocalDateTime.MIN, LocalDateTime.MAX);
    DateTimeRange startTimeGreaterEndTimeRange = new DateTimeRange(LocalDateTime.MAX, LocalDateTime.MIN);
    DateTimeRange startTimeEqualsEndTimeRange = new DateTimeRange(LocalDateTime.MIN, LocalDateTime.MIN);

    assertFalse(dateTimeRangeValidator.isValid(nullStartTimeRange, null));
    assertFalse(dateTimeRangeValidator.isValid(nullEndTimeRange, null));
    assertTrue(dateTimeRangeValidator.isValid(startTimeLessEndTimeRange, null));
    assertFalse(dateTimeRangeValidator.isValid(startTimeGreaterEndTimeRange, null));
    assertTrue(dateTimeRangeValidator.isValid(startTimeEqualsEndTimeRange, null));
  }

}