package com.sudoku.system.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.system.mapper.ResourceMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import utils.UserMockUtils;

/**
 * 资源业务层实现类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

  private ResourceService resourceService;

  @Mock
  private ResourceMapper resourceMapper;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    resourceService = new ResourceService(resourceMapper);
  }

  /**
   * 使用管理员用户测试获取其资源列表
   */
  @Test
  public void testGetUserPermissionWithAdminUser() {
    Set<String> permissions = resourceService.getUserPermission(UserMockUtils.getAdminUser());
    assertEquals(Collections.singleton(PermissionConstants.ADMIN_PERMISSION), permissions);
  }

  /**
   * 使用普通用户测试获取其资源列表
   */
  @Test
  public void testGetUserPermissionWithNormalUser() {
    HashSet<String> normalUserPermissions = new HashSet<>(
        Arrays.asList("sudoku:rank:list", "sudoku:user:information", "statistics:user:list"));
    when(resourceMapper.selectPermsByUserId(any())).thenReturn(normalUserPermissions);

    Set<String> permissions = resourceService.getUserPermission(UserMockUtils.getNormalUser());
    assertEquals(normalUserPermissions, permissions);
  }
}