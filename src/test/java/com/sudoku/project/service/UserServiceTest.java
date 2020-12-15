package com.sudoku.project.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.exception.UserException;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.convert.UserConvert;
import com.sudoku.project.mapper.MergeUserRoleMapper;
import com.sudoku.project.mapper.RoleMapper;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.body.AddUserBody;
import com.sudoku.project.model.body.ModifyUserBody;
import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * 用户业务层实现类的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityUtils.class})
public class UserServiceTest {

  private UserService userService;

  @Mock
  private UserMapper userMapper;

  @Mock
  private RoleMapper roleMapper;

  @Mock
  private MergeUserRoleMapper mergeUserRoleMapper;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    userService = new UserService(userMapper, roleMapper, mergeUserRoleMapper, Mappers.getMapper(UserConvert.class));
  }

  /**
   * 使用已存在的用户名测试注册用户
   */
  @Test(expected = UserException.class)
  public void testRegisterUserIfExistUsername() {
    String username = "existUsername";
    User user = new User(username, "password", "nickname", true);
    when(userMapper.selectByUsername(username)).thenReturn(user);
    mockStatic(SecurityUtils.class);

    userService.registerUser(RegisterUserBody.builder().username(username).build());
  }

  /**
   * 测试修改管理员用户信息
   */
  @Test(expected = UserException.class)
  public void testModifyUserForAdminUser() {
    ModifyUserBody modifyUserBody = ModifyUserBody.builder().id(1).build();
    when(roleMapper.selectNameZhByUserId(modifyUserBody.getId())).thenReturn(PermissionConstants.ADMIN_ROLE_NAME);

    userService.modifyUser(modifyUserBody);
  }

  /**
   * 测试修改不存在的用户的信息
   */
  @Test(expected = UserException.class)
  public void testModifyUserIfNotExistUser() {
    when(roleMapper.selectNameZhByUserId(any())).thenReturn(null);

    userService.modifyUser(new ModifyUserBody());
  }

  /**
   * 测试修改的用户名已在系统中以存在的情况
   */
  @Test(expected = UserException.class)
  public void testModifyUserIfExistUsername() {
    String username = "existUsername";
    int id = 1;
    ModifyUserBody modifyUserBody = ModifyUserBody.builder().id(id).username(username).build();
    User user = new User();
    user.setId(id);

    when(roleMapper.selectNameZhByUserId(modifyUserBody.getId())).thenReturn(PermissionConstants.USER_ROLE_NAME);
    when(userMapper.selectByUsername(username)).thenReturn(user);

    userService.modifyUser(new ModifyUserBody());
  }

  /**
   * 使用已存在的用户名测试注册用户
   */
  @Test(expected = UserException.class)
  public void testAddUserIfExistUsername() {
    String username = "existUsername";
    User user = new User(username, "password", "nickname", true);
    when(userMapper.selectByUsername(username)).thenReturn(user);

    userService.addUser(AddUserBody.builder().username(username).build());
  }

}