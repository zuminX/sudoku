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
 * 验证数独矩阵注解
 */
@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsSudokuMatrixValidator.class)
public @interface IsSudokuMatrix {

  String message() default "该数组必须是一个数独矩阵";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
