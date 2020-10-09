package com.sudoku.common.validator;

import com.sudoku.common.tools.DateTimeRange;
import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 时间日期范围类的验证器
 */
public class IsDateTimeRangeValidator implements ConstraintValidator<IsDateTimeRange, DateTimeRange> {

  /**
   * 初始化验证器
   *
   * @param constraint 验证时间日期范围类的注解
   */
  public void initialize(IsDateTimeRange constraint) {
  }

  /**
   * 校验指定范围的开始的日期时间是否早于结束的日期时间
   *
   * @param range   待校验的范围对象
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(DateTimeRange range, ConstraintValidatorContext context) {
    if (range == null) {
      return true;
    }
    LocalDateTime start = range.getStart();
    LocalDateTime end = range.getEnd();
    return start == null || end == null || end.compareTo(start) >= 0;
  }
}
