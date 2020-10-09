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
@Constraint(validatedBy = IsDateTimeRangeValidator.class)
public @interface IsDateTimeRange {

  String message() default "开始的日期时间必须早于结束的日期时间";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
