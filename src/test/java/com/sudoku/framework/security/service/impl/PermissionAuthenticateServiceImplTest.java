package com.sudoku.framework.security.service.impl;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.sudoku.common.tools.ServletUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * 权限验证业务层实现类的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ServletUtils.class})
public class PermissionAuthenticateServiceImplTest {

  private PermissionAuthenticateServiceImpl permissionAuthenticateService;

  @Mock
  private UserTokenServiceImpl userTokenService;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() throws Exception {
    permissionAuthenticateService = new PermissionAuthenticateServiceImpl(userTokenService);
    mockStatic(ServletUtils.class);
  }

  @Test
  public void hasPermission() {
  }

  @Test
  public void hasRole() {
  }
}