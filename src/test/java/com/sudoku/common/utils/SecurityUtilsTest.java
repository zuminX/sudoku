package com.sudoku.common.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.framework.security.model.LoginUserBO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import utils.UserMockUtils;

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
    assertTrue(SecurityUtils.isAdmin(UserMockUtils.getAdminUser()));
    assertFalse(SecurityUtils.isAdmin(UserMockUtils.getNormalUser()));
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
    assertFalse(SecurityUtils.isLoginUserRolesEmpty(UserMockUtils.getAdminLoginUser()));
    assertTrue(SecurityUtils.isLoginUserRolesEmpty(null));
    assertTrue(SecurityUtils.isLoginUserRolesEmpty(new LoginUserBO()));
  }

  /**
   * 测试判断登录用户的权限是否为空
   */
  @Test
  public void testIsLoginUserPermissionsEmpty() {
    assertFalse(SecurityUtils.isLoginUserPermissionsEmpty(UserMockUtils.getAdminLoginUser()));
    assertTrue(SecurityUtils.isLoginUserPermissionsEmpty(null));
    assertTrue(SecurityUtils.isLoginUserPermissionsEmpty(new LoginUserBO()));
  }
}
