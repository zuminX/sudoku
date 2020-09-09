package com.sudoku.service.impl;

import com.sudoku.mapper.ResourceMapper;
import com.sudoku.model.entity.User;
import com.sudoku.service.ResourceService;
import com.sudoku.utils.SecurityUtils;
import java.util.Collections;
import java.util.Set;
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
