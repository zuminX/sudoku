package com.sudoku.framework.security.service.impl;

import static com.sudoku.common.utils.SecurityUtils.isAdmin;
import static com.sudoku.common.utils.SecurityUtils.isLoginUserPermissionsEmpty;
import static com.sudoku.common.utils.SecurityUtils.isLoginUserRolesEmpty;

import cn.hutool.core.util.StrUtil;
import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.tools.ServletUtils;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.framework.security.service.PermissionAuthenticateService;
import com.sudoku.framework.security.service.UserTokenService;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * 权限验证业务层实现类
 */
@Service("ss")
public class PermissionAuthenticateServiceImpl implements PermissionAuthenticateService {

  private final UserTokenService tokenService;

  public PermissionAuthenticateServiceImpl(UserTokenService tokenService) {
    this.tokenService = tokenService;
  }

  /**
   * 验证当前用户是否具有指定权限
   *
   * @param permission 权限
   * @return 具有该权限返回true，否则返回false
   */
  @Override
  public boolean hasPermission(String permission) {
    if (StrUtil.isBlank(permission)) {
      return false;
    }
    LoginUserBO loginUserBO = getLoginUser();
    if (isLoginUserPermissionsEmpty(loginUserBO)) {
      return false;
    }
    Set<String> permissions = loginUserBO.getPermissions();
    return permissions.contains(PermissionConstants.ADMIN_PERMISSION) || permissions.contains(StrUtil.trim(permission));
  }

  /**
   * 验证当前用户是否具有指定角色
   *
   * @param role 角色名
   * @return 具有该角色返回true，否则返回false
   */
  @Override
  public boolean hasRole(String role) {
    if (StrUtil.isBlank(role)) {
      return false;
    }
    LoginUserBO loginUserBO = getLoginUser();
    if (isLoginUserRolesEmpty(loginUserBO)) {
      return false;
    }
    if (isAdmin(loginUserBO.getUser())) {
      return true;
    }
    String trimRole = StrUtil.trim(role);
    return loginUserBO.getUser().getRoles().stream().anyMatch(r -> r.getName().equals(trimRole));
  }

  /**
   * 根据请求头中的token获取登录用户对象
   *
   * @return 登录用户对象
   */
  private LoginUserBO getLoginUser() {
    return tokenService.getLoginUser(ServletUtils.getRequest());
  }
}
