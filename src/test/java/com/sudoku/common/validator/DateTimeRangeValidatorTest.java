package com.sudoku.common.validator;

import static org.junit.Assert.assertEquals;
import static org.powermock.reflect.Whitebox.setInternalState;

import com.sudoku.common.tools.DateTimeRange;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 时间日期范围类的验证器类的测试类
 */
@RunWith(Parameterized.class)
public class DateTimeRangeValidatorTest {

  private final DateTimeRange range;

  private final boolean startTimeNotNull;

  private final boolean endTimeNotNull;

  private final boolean expected;

  /**
   * 用于参数化的构造方法
   *
   * @param range            日期时间范围
   * @param startTimeNotNull 开始时间不能为空
   * @param endTimeNotNull   结束时间不能为空
   * @param expected         预期结果
   */
  public DateTimeRangeValidatorTest(DateTimeRange range, boolean startTimeNotNull, boolean endTimeNotNull, boolean expected) {
    this.range = range;
    this.startTimeNotNull = startTimeNotNull;
    this.endTimeNotNull = endTimeNotNull;
    this.expected = expected;
  }

  /**
   * 参数化数据
   *
   * @return 数据
   */
  @Parameters
  public static Collection<Object[]> boundaryValueData() {
    LocalDateTime early = LocalDateTime.of(2020, 1, 1, 0, 0), late = LocalDateTime.of(2020, 1, 2, 0, 0);
    return Arrays.asList(new Object[][]{
        {null, false, false, true},
        {new DateTimeRange(null, null), true, false, false},
        {new DateTimeRange(null, null), false, true, false},
        {new DateTimeRange(null, null), false, false, true},
        {new DateTimeRange(early, null), false, false, true},
        {new DateTimeRange(early, late), false, false, true},
        {new DateTimeRange(late, early), false, false, false},
    });
  }

  /**
   * 测试校验指定范围的开始的日期时间是否早于结束的日期时间
   */
  @Test
  public void testIsValid() {
    DateTimeRangeValidator dateTimeRangeValidator = new DateTimeRangeValidator();
    setInternalState(dateTimeRangeValidator, "startTimeNotNull", startTimeNotNull);
    setInternalState(dateTimeRangeValidator, "endTimeNotNull", endTimeNotNull);

    assertEquals(expected, dateTimeRangeValidator.isValid(range, null));
  }
}