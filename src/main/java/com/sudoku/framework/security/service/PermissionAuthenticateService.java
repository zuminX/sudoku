package com.sudoku.framework.security.service;

/**
 * 权限验证业务层接口
 */
public interface PermissionAuthenticateService {

  /**
   * 验证当前用户是否具有指定权限
   *
   * @param permission 权限
   * @return 具有该权限返回true，否则返回false
   */
  boolean hasPermission(String permission);

  /**
   * 验证当前用户是否具有指定角色
   *
   * @param role 角色名
   * @return 具有该角色返回true，否则返回false
   */
  boolean hasRole(String role);
}
