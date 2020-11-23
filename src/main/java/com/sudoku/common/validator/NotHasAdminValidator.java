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
   * 校验角色名列表中是否含有管理员
   *
   * @param roleNameList 角色名列表
   * @param context      约束校验器上下文对象
   * @return 验证通过返回true，验证失败返回false
   */
  public boolean isValid(List<String> roleNameList, ConstraintValidatorContext context) {
    return CollUtil.isEmpty(roleNameList) || !SecurityUtils.hasAdmin(roleNameList);
  }
}
