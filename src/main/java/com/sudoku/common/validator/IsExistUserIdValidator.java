package com.sudoku.common.validator;

import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.common.utils.project.UserUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数独矩阵验证器
 */
public class IsExistUserIdValidator implements ConstraintValidator<IsExistUserId, Integer> {

  /**
   * 初始化验证器
   *
   * @param constraint 验证用户ID是否存在的注解
   */
  @Override
  public void initialize(IsExistUserId constraint) {
  }

  /**
   * 校验用户ID是否存在
   *
   * @param value   待校验的数组
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return SpringUtil.getBean(UserUtils.class).userIdIsExist(value);
  }
}
