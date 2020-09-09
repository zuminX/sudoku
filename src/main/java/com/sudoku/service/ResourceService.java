package com.sudoku.service;

import com.sudoku.model.entity.User;
import java.util.Set;

/**
 * 资源业务层接口
 */
public interface ResourceService {

  /**
   * 获取所有资源及其拥有的角色
   *
   * @param user 用户对象
   * @return 资源列表
   */
  Set<String> getUserPermission(User user);
}
