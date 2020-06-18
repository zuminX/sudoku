package com.sudoku.service.impl;

import com.sudoku.mapper.RoleMapper;
import com.sudoku.mapper.UserMapper;
import com.sudoku.model.entity.User;
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
  private UserMapper userMapper;
  @Autowired
  private RoleMapper roleMapper;

  /**
   * 根据用户名查找用户
   *
   * @param username 用户名
   * @return 用户对象
   */
  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userMapper.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("用户名不存在");
    }
    user.setRoles(roleMapper.selectByUserId(user.getId()));
    return user;
  }
}
