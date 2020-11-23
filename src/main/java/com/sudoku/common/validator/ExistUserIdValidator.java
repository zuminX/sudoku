package com.sudoku.common.validator;

import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.common.utils.project.UserUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用户ID验证器类
 */
public class ExistUserIdValidator implements ConstraintValidator<IsExistUserId, Integer> {

  /**
   * 校验用户ID是否存在
   *
   * @param value   待校验的数组
   * @param context 约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return SpringUtil.getBean(UserUtils.class).existUserId(value);
  }
}
