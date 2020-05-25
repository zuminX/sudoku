package com.sudoku.service.impl;

import com.sudoku.mapper.ResourceMapper;
import com.sudoku.model.po.Resource;
import com.sudoku.service.ResourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源业务层实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceMapper resourceMapper;

  /**
   * 获取所有资源及其拥有的角色
   *
   * @return 资源列表
   */
  @Override
  public List<Resource> getAllResourcesWithRole() {
    return resourceMapper.selectAllResourcesWithRole();
  }
}
