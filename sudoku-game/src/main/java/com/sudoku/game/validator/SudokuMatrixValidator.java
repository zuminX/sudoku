package com.sudoku.game.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数独矩阵验证器类
 */
public class SudokuMatrixValidator {

  /**
   * 私有构造方法，防止实例化
   */
  private SudokuMatrixValidator() {
  }

  /**
   * 校验二维列表是否为数独矩阵
   *
   * @param value 待校验的列表
   * @return 验证通过返回true，验证失败返回false
   */
  protected static <T> boolean isValid(List<List<T>> value) {
    return value != null && value.size() == 9 && value.stream().noneMatch(list -> list.size() != 9);
  }

  public static class BooleanSudokuMatrixValidator implements ConstraintValidator<IsSudokuMatrix, List<List<Boolean>>> {

    /**
     * 校验二维列表是否为数独矩阵
     *
     * @param value   待校验的列表
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
     * 校验二维列表是否为数独矩阵
     *
     * @param value   待校验的列表
     * @param context 约束校验器上下文对象
     * @return 验证通过返回true，验证失败返回false
     */
    @Override
    public boolean isValid(List<List<Integer>> value, ConstraintValidatorContext context) {
      return SudokuMatrixValidator.isValid(value);
    }
  }
}
