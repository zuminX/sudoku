package com.sudoku.system.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 验证角色名列表注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NotHasAdminValidator.class)
public @interface NotHasAdmin {

  String message() default "角色名列表中不能有管理员";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
