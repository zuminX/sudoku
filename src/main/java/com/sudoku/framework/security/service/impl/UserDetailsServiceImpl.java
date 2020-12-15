package com.sudoku.framework.security.service.impl;

import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.UserException;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.service.ResourceService;
import com.sudoku.project.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情业务层实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;
  private final ResourceService resourceService;

  public UserDetailsServiceImpl(UserService userService, ResourceService resourceService) {
    this.userService = userService;
    this.resourceService = resourceService;
  }

  /**
   * 根据用户名加载用户对象
   *
   * @param username 用户名
   * @return 用户详情对象
   */
  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userService.selectWithRoleByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("该用户不存在");
    }
    if (!user.getEnabled()) {
      throw new UserException(StatusCode.USER_NOT_ENABLED);
    }
    return new LoginUserBO(user, resourceService.getUserPermission(user));
  }
}
