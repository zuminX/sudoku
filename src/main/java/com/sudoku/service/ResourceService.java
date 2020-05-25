package com.sudoku.service;

import com.sudoku.model.po.Resource;
import java.util.List;

/**
 * 资源业务层接口
 */
public interface ResourceService {

  /**
   * 获取所有资源及其拥有的角色
   *
   * @return 资源列表
   */
  List<Resource> getAllResourcesWithRole();
}
