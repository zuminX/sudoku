package com.sudoku.common.validator;

import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.common.utils.project.SudokuLevelUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数独等级ID验证器类
 */
public class ExistSudokuLevelIdValidator implements ConstraintValidator<IsExistSudokuLevelId, Integer> {

  /**
   * 校验数独等级ID是否存在
   *
   * @param value   待校验的数组
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return SpringUtil.getBean(SudokuLevelUtils.class).existId(value);
  }
}
