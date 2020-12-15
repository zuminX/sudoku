package com.sudoku.project.service;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.mapper.ResourceMapper;
import com.sudoku.project.model.entity.User;
import java.util.Collections;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * 资源业务层类
 */
@Service
public class ResourceService {

  private final ResourceMapper resourceMapper;

  public ResourceService(ResourceMapper resourceMapper) {
    this.resourceMapper = resourceMapper;
  }

  /**
   * 获取该用户的资源列表
   *
   * @param user 用户对象
   * @return 资源列表
   */
  public Set<String> getUserPermission(User user) {
    return SecurityUtils.isAdmin(user) ? Collections.singleton(PermissionConstants.ADMIN_PERMISSION)
        : resourceMapper.selectPermsByUserId(user.getId());
  }
}
