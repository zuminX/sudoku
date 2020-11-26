package com.sudoku.common.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.project.model.entity.Role;
import com.sudoku.project.model.entity.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 安全服务工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class SecurityUtilsTest {

  /**
   * 测试判断用户是否为管理员
   */
  @Test
  public void testIsAdmin() {
    User adminUser = new User();
    adminUser.setRoles(generateRoleList(PermissionConstants.ADMIN_ROLE_NAME));
    User normalUser = new User();
    normalUser.setRoles(generateRoleList(PermissionConstants.USER_ROLE_NAME));

    assertTrue(SecurityUtils.isAdmin(adminUser));
    assertFalse(SecurityUtils.isAdmin(normalUser));
  }


  /**
   * 测试判断角色名列表是否包含管理员
   */
  @Test
  public void testHasAdmin() {
    assertTrue(SecurityUtils.hasAdmin(PermissionConstants.ADMIN_ROLE_NAME));
    assertFalse(SecurityUtils.hasAdmin(PermissionConstants.USER_ROLE_NAME));
  }

  /**
   * 测试判断登录用户的角色是否为空
   */
  @Test
  public void testIsLoginUserRolesEmpty() {
    User user = new User();
    user.setRoles(generateRoleList(PermissionConstants.USER_ROLE_NAME));
    LoginUserBO hasRolesLoginUser = new LoginUserBO();
    hasRolesLoginUser.setUser(user);

    assertFalse(SecurityUtils.isLoginUserRolesEmpty(hasRolesLoginUser));
    assertTrue(SecurityUtils.isLoginUserRolesEmpty(null));
    assertTrue(SecurityUtils.isLoginUserRolesEmpty(new LoginUserBO()));
  }

  /**
   * 测试判断登录用户的权限是否为空
   */
  @Test
  public void testIsLoginUserPermissionsEmpty() {
    LoginUserBO hasPermissionsLoginUser = new LoginUserBO();
    hasPermissionsLoginUser.setPermissions(Collections.singleton(PermissionConstants.ADMIN_PERMISSION));

    assertFalse(SecurityUtils.isLoginUserPermissionsEmpty(hasPermissionsLoginUser));
    assertTrue(SecurityUtils.isLoginUserPermissionsEmpty(null));
    assertTrue(SecurityUtils.isLoginUserPermissionsEmpty(new LoginUserBO()));
  }

  /**
   * 根据角色名列表模拟生成角色列表
   *
   * @param roleNameList 角色名列表
   * @return 角色列表
   */
  private List<Role> generateRoleList(@NotNull List<String> roleNameList) {
    return roleNameList.stream()
        .map(roleName -> new Role(1, roleName, roleName))
        .collect(Collectors.toList());
  }
}
