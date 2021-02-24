package com.sudoku.system.security.service;


import cn.hutool.core.util.StrUtil;
import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.tools.ServletUtils;
import com.sudoku.system.model.bo.LoginUserBO;
import com.sudoku.system.utils.SecurityUtils;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * 权限验证业务层实现类
 */
@Service("ss")
public class PermissionAuthenticateService {

  private final UserTokenService tokenService;

  public PermissionAuthenticateService(UserTokenService tokenService) {
    this.tokenService = tokenService;
  }

  /**
   * 验证当前用户是否具有指定权限
   *
   * @param permission 权限
   * @return 具有该权限返回true，否则返回false
   */
  public boolean hasPermission(String permission) {
    if (StrUtil.isBlank(permission)) {
      return false;
    }
    LoginUserBO loginUserBO = getLoginUser();
    if (SecurityUtils.isLoginUserPermissionsEmpty(loginUserBO)) {
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
  public boolean hasRole(String role) {
    if (StrUtil.isBlank(role)) {
      return false;
    }
    LoginUserBO loginUserBO = getLoginUser();
    if (SecurityUtils.isLoginUserRolesEmpty(loginUserBO)) {
      return false;
    }
    if (SecurityUtils.isAdmin(loginUserBO.getUser())) {
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
