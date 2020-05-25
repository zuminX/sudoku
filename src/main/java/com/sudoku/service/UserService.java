package com.sudoku.service;

import com.sudoku.model.vo.RegisterUserVO;
import com.sudoku.model.vo.UserVO;

/**
 * 用户业务层接口
 */
public interface UserService {

  /**
   * 注册用户
   *
   * @param registerUserVO 注册用户对象
   * @return 用户显示层对象
   */
  UserVO registerUser(RegisterUserVO registerUserVO);
}
