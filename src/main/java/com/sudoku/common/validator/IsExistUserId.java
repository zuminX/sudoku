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
 * 验证用户ID是否存在的注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ExistUserIdValidator.class)
public @interface IsExistUserId {

  String message() default "指定用户ID在系统中不存在";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
