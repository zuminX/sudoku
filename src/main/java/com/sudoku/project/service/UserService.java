package com.sudoku.project.service;

import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.vo.UserVO;

/**
 * 用户业务层接口
 */
public interface UserService {

  /**
   * 注册用户
   *
   * @param registerUserBody 注册用户对象
   * @return 用户显示层对象
   */
  UserVO registerUser(RegisterUserBody registerUserBody);

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  User selectWithRoleByUsername(String username);

  /**
   * 更新用户最近登录的时间
   *
   * @param userId 用户ID
   */
  void updateRecentLoginTime(Integer userId);
}
