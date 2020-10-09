package com.sudoku.common.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数独矩阵验证器
 */
public class IsSudokuMatrixValidator implements ConstraintValidator<IsSudokuMatrix, List<List<Integer>>> {

  /**
   * 初始化验证器
   *
   * @param constraint 验证数独矩阵注解
   */
  @Override
  public void initialize(IsSudokuMatrix constraint) {
  }

  /**
   * 校验二维数组是否为数独矩阵
   *
   * @param value   待校验的数组
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(List<List<Integer>> value, ConstraintValidatorContext context) {
    return value != null && value.size() == 9 && value.stream().noneMatch(list -> list.size() != 9);
  }
}
