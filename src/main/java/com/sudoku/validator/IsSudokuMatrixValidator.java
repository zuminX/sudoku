package com.sudoku.validator;

import java.util.ArrayList;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数独矩阵验证器
 */
public class IsSudokuMatrixValidator implements ConstraintValidator<IsSudokuMatrix, ArrayList<ArrayList<Integer>>> {

  public void initialize(IsSudokuMatrix constraint) {
  }

  /**
   * 校验二维数组是否为数独矩阵
   *
   * @param value   带校验的数组
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(ArrayList<ArrayList<Integer>> value, ConstraintValidatorContext context) {
    return value.size() == 9 && value.stream().noneMatch(list -> list.size() != 9);
  }
}
