package com.sudoku.common.validator;

import cn.hutool.core.collection.CollUtil;
import com.sudoku.common.utils.SecurityUtils;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 角色名列表验证器
 */
public class NotHasAdminValidator implements ConstraintValidator<NotHasAdmin, List<String>> {

  /**
   * 初始化验证器
   *
   * @param constraint 验证角色名列表注解
   */
  public void initialize(NotHasAdmin constraint) {
  }

  public boolean isValid(List<String> roleNameList, ConstraintValidatorContext context) {
    return CollUtil.isEmpty(roleNameList) || !SecurityUtils.hasAdmin(roleNameList);
  }
}
