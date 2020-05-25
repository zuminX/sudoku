package com.sudoku.constant.consist;

import java.util.List;

/**
 * 角色对应数据库的名字
 */
public interface RoleName {

  List<String> USER = List.of("ROLE_USER");

  List<String> ADMIN = List.of("ROLE_USER", "ROLE_ADMIN");
}
