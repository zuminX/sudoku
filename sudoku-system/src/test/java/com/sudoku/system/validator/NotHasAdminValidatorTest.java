package com.sudoku.system.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.sudoku.common.constant.consist.PermissionConstants;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 角色名列表验证器类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class NotHasAdminValidatorTest {

  private NotHasAdminValidator notHasAdminValidator;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    notHasAdminValidator = new NotHasAdminValidator();
  }

  /**
   * 测试校验角色名列表中是否含有管理员
   */
  @Test
  public void testIsValid() {
    assertFalse(notHasAdminValidator.isValid(PermissionConstants.ADMIN_ROLE_NAME, null));
    assertTrue(notHasAdminValidator.isValid(PermissionConstants.USER_ROLE_NAME, null));
  }

  /**
   * 测试校验空的角色名列表中是否含有管理员
   */
  @Test
  public void testIsValidWithEmpty() {
    assertTrue(notHasAdminValidator.isValid(null, null));
    assertTrue(notHasAdminValidator.isValid(new ArrayList<>(), null));
  }
}