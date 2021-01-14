package com.sudoku.framework.security.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.sudoku.common.exception.UserException;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.service.ResourceService;
import com.sudoku.project.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户详情业务层实现类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

  @Mock
  private UserService userService;

  @Mock
  private ResourceService resourceService;

  private UserDetailsServiceImpl userDetailsService;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    userDetailsService = new UserDetailsServiceImpl(userService, resourceService);
  }

  /**
   * 使用正常启用的用户测试加载用户对象
   */
  @Test
  public void testLoadUserByUsernameWithEnabledUser() {
    User enabledUser = new User();
    enabledUser.setUsername("enabledUser");
    enabledUser.setEnabled(true);
    mockSelectUser(enabledUser);

    assertEquals(enabledUser.getUsername(), userDetailsService.loadUserByUsername(enabledUser.getUsername()).getUsername());
  }

  /**
   * 使用禁用的用户测试加载用户对象
   */
  @Test(expected = UserException.class)
  public void testLoadUserByUsernameWithDisabledUser() {
    User disabledUser = new User();
    disabledUser.setUsername("disabledUser");
    disabledUser.setEnabled(false);
    mockSelectUser(disabledUser);

    userDetailsService.loadUserByUsername(disabledUser.getUsername());
  }

  /**
   * 使用不存在该用户名的用户测试加载用户对象
   */
  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByUsernameIfNotExistUser() {
    mockSelectUser(null);
    userDetailsService.loadUserByUsername("notExistUserName");
  }

  /**
   * 模拟查询出的用户
   *
   * @param user 查询返回的用户
   */
  private void mockSelectUser(User user) {
    when(userService.selectWithRoleByUsername(anyString())).thenReturn(user);
  }
}