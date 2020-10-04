package com.sudoku.project.service;

import com.sudoku.common.tools.page.Page;
import com.sudoku.project.model.body.AddUserBody;
import com.sudoku.project.model.body.ModifyUserBody;
import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.vo.UserDetailVO;
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

  /**
   * 获取系统用户列表
   *
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 用户详情的分页信息
   */
  Page<UserDetailVO> getUserList(Integer page, Integer pageSize);

  /**
   * 修改用户信息
   *
   * @param modifyUserBody 修改的用户信息
   */
  void modifyUser(ModifyUserBody modifyUserBody);

  /**
   * 新增用户
   *
   * @param addUserBody 新增用户的信息
   */
  void addUser(AddUserBody addUserBody);
}
