package com.sudoku.project.service.impl;

import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.mapper.ResourceMapper;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.service.ResourceService;
import java.util.Collections;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * 资源业务层实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

  private final ResourceMapper resourceMapper;

  public ResourceServiceImpl(ResourceMapper resourceMapper) {
    this.resourceMapper = resourceMapper;
  }

  /**
   * 获取所有资源及其拥有的角色
   *
   * @param user 用户对象
   * @return 资源列表
   */
  @Override
  public Set<String> getUserPermission(User user) {
    if (SecurityUtils.isAdmin(user)) {
      return Collections.singleton("*:*:*");
    }
    return resourceMapper.selectPermsByUserId(user.getId());
  }
}
