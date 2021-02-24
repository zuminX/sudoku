package com.sudoku.common.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证时间日期范围类的注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateTimeRangeValidator.class)
public @interface IsDateTimeRange {

  String message() default "开始的日期时间必须早于结束的日期时间";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * 若该属性为false，则对于任意一个开始日期时间为null的时间日期范围对象而言，校验都会通过
   *
   * @return 开始的日期时间是否不能为null
   */
  boolean startNotNull() default false;

  /**
   * 若该属性为false，则对于任意一个结束日期时间为null的时间日期范围对象而言，校验都会通过
   *
   * @return 结束的日期时间是否不能为null
   */
  boolean endTimeNotNull() default false;
}
