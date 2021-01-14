package com.sudoku.framework.security.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.tools.ServletUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import utils.UserMockUtils;

/**
 * 权限验证业务层实现类的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ServletUtils.class})
public class PermissionAuthenticateServiceTest {

  private PermissionAuthenticateService permissionAuthenticateService;

  @Mock
  private UserTokenService userTokenService;

  /**
   * 普通用户的权限名集合
   */
  private Set<String> normalUserPermissions;

  /**
   * 管理员用户的权限名集合
   */
  private Set<String> adminUserPermissions;

  /**
   * 非管理员用户角色和普通用户角色的角色名
   */
  private String otherRole;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() throws Exception {
    mockStatic(ServletUtils.class);

    permissionAuthenticateService = new PermissionAuthenticateService(userTokenService);

    normalUserPermissions = new HashSet<>();
    normalUserPermissions.addAll(Arrays.asList("sudoku:rank:list", "sudoku:user:information", "statistics:user:list"));
    adminUserPermissions = new HashSet<>();
    adminUserPermissions.addAll(Arrays.asList("sudoku:final:generate", "sudoku:race:add", "system:user:search"));
    otherRole = "ROLE_TEST";
  }

  /**
   * 使用普通用户测试验证用户是否具有指定权限
   */
  @Test
  public void testHasPermissionWithNormalUser() {
    mockNormalLoginUser();

    adminUserPermissions.stream().map(s -> permissionAuthenticateService.hasPermission(s)).forEach(Assert::assertFalse);
    normalUserPermissions.stream().map(s -> permissionAuthenticateService.hasPermission(s)).forEach(Assert::assertTrue);
  }

  /**
   * 使用管理员用户测试验证用户是否具有指定权限
   */
  @Test
  public void testHasPermissionWithAdminUser() {
    mockAdminLoginUser();

    adminUserPermissions.stream().map(s -> permissionAuthenticateService.hasPermission(s)).forEach(Assert::assertTrue);
    normalUserPermissions.stream().map(s -> permissionAuthenticateService.hasPermission(s)).forEach(Assert::assertTrue);
  }

  /**
   * 使用普通用户测试验证用户是否具有指定角色
   */
  @Test
  public void testHasRoleWithNormalUser() {
    mockNormalLoginUser();

    assertFalse(permissionAuthenticateService.hasRole(otherRole));
    PermissionConstants.USER_ROLE_NAME.stream().map(s -> permissionAuthenticateService.hasRole(s)).forEach(Assert::assertTrue);
  }

  /**
   * 使用管理员用户测试验证用户是否具有指定角色
   */
  @Test
  public void testHasRoleWithAdminUser() {
    mockAdminLoginUser();

    assertTrue(permissionAuthenticateService.hasRole(otherRole));
    PermissionConstants.ADMIN_ROLE_NAME.stream().map(s -> permissionAuthenticateService.hasRole(s)).forEach(Assert::assertTrue);
  }

  /**
   * 模拟当前登录用户为管理员
   */
  private void mockAdminLoginUser() {
    when(userTokenService.getLoginUser(any())).thenReturn(UserMockUtils.getAdminLoginUser(normalUserPermissions));
  }

  /**
   * 模拟当前登录用户为普通用户
   */
  private void mockNormalLoginUser() {
    when(userTokenService.getLoginUser(any())).thenReturn(UserMockUtils.getNormalLoginUser(normalUserPermissions));
  }

}