package com.sudoku.security.service.impl;

import com.sudoku.constant.enums.StatusCode;
import com.sudoku.exception.UserException;
import com.sudoku.model.entity.User;
import com.sudoku.security.model.LoginUserBO;
import com.sudoku.service.ResourceService;
import com.sudoku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情业务层实现类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserService userService;
  @Autowired
  private ResourceService resourceService;

  /**
   * 根据用户名查询用户对象
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
