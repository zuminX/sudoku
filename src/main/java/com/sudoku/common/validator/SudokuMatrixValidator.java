package com.sudoku.common.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数独矩阵验证器
 */
public class SudokuMatrixValidator {

  /**
   * 校验二维数组是否为数独矩阵
   *
   * @param value 待校验的数组
   * @return 验证通过返回true，验证失败返回false
   */
  private static <T> boolean isValid(List<List<T>> value) {
    return value != null && value.size() == 9 && value.stream().noneMatch(list -> list.size() != 9);
  }

  public static class BooleanSudokuMatrixValidator implements ConstraintValidator<IsSudokuMatrix, List<List<Boolean>>> {

    /**
     * 校验二维数组是否为数独矩阵
     *
     * @param value   待校验的数组
     * @param context 约束校验器上下文对象
     * @return 验证通过返回true，验证失败返回false
     */
    @Override
    public boolean isValid(List<List<Boolean>> value, ConstraintValidatorContext context) {
      return SudokuMatrixValidator.isValid(value);
    }
  }

  public static class IntegerSudokuMatrixValidator implements ConstraintValidator<IsSudokuMatrix, List<List<Integer>>> {

    /**
     * 校验二维数组是否为数独矩阵
     *
     * @param value   待校验的数组
     * @param context 约束校验器上下文对象
     * @return 验证通过返回true，验证失败返回false
     */
    @Override
    public boolean isValid(List<List<Integer>> value, ConstraintValidatorContext context) {
      return SudokuMatrixValidator.isValid(value);
    }
  }
}
