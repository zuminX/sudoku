package com.sudoku.common.constant.consist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 权限常量类
 */
public class PermissionConstants {

  /**
   * 管理员名
   */
  public static final String ADMIN_NAME = "ROLE_ADMIN";

  /**
   * 用户名
   */
  public static final String USER_NAME = "ROLE_USER";

  /**
   * 用户角色名
   */
  public static final List<String> USER_ROLE_NAME = Collections.singletonList(USER_NAME);

  /**
   * 管理员角色名
   */
  public static final List<String> ADMIN_ROLE_NAME = Arrays.asList(ADMIN_NAME, USER_NAME);

  /**
   * 管理员权限标志
   */
  public static final String ADMIN_PERMISSION = "*:*:*";

  /**
   * 私有构造方法，防止实例化
   */
  private PermissionConstants() {
  }
}
