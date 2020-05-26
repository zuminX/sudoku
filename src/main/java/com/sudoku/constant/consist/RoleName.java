package com.sudoku.constant.consist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 角色对应数据库的名字
 */
public interface RoleName {

  List<String> USER = Collections.singletonList("ROLE_USER");

  List<String> ADMIN = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
}
