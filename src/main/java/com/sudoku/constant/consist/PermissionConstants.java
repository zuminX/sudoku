package com.sudoku.constant.consist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 权限常量类
 */
public class PermissionConstants {

  public static final String ADMIN_NAME = "ROLE_ADMIN";

  public static final String USER_NAME = "ROLE_USER";

  public static final List<String> USER_ROLE_NAME = Collections.singletonList(USER_NAME);

  public static final List<String> ADMIN_ROLE_NAME = List.of(ADMIN_NAME, USER_NAME);

  public static final String ADMIN_PERMISSION = "*:*:*";

  private PermissionConstants() {
  }
}
