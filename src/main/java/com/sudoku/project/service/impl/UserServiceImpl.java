package com.sudoku.project.service.impl;

import static java.util.stream.Collectors.toCollection;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.project.convert.UserConvert;
import com.sudoku.common.exception.UserException;
import com.sudoku.common.log.Log;
import com.sudoku.project.mapper.RoleMapper;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.mapper.UserRoleMapper;
import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.entity.UserRole;
import com.sudoku.project.model.vo.UserVO;
import com.sudoku.project.service.UserService;
import com.sudoku.common.utils.SecurityUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务层实现类
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;
  @Autowired
  private RoleMapper roleMapper;
  @Autowired
  private UserRoleMapper userRoleMapper;
  @Autowired
  private SecurityUtils securityUtils;
  @Autowired
  private UserConvert userConvert;

  /**
   * 注册用户
   *
   * @param registerUserBody 注册用户对象
   * @return 用户显示层对象
   */
  @Override
  @Transactional
  @Log("注册用户")
  public UserVO registerUser(RegisterUserBody registerUserBody) {
    checkRepeatPassword(registerUserBody);

    User user = convertToUser(registerUserBody);
    checkUsername(user);

    userMapper.insert(user);
    insertUserRole(user);

    return userConvert.convert(user);
  }

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  @Override
  public User selectWithRoleByUsername(String username) {
    return userMapper.selectWithRoleByUsername(username);
  }

  /**
   * 更新用户最近登录的时间
   *
   * @param userId 用户ID
   */
  @Override
  public void updateRecentLoginTime(Integer userId) {
    userMapper.updateRecentLoginTimeById(LocalDateTime.now(), userId);
  }

  /**
   * 检查注册的用户名在数据库中是否已经存在
   *
   * @param user 用户对象
   */
  private void checkUsername(User user) {
    if (userMapper.selectByUsername(user.getUsername()) != null) {
      throw new UserException(StatusCode.USER_HAS_EQUAL_NAME);
    }
  }

  /**
   * 检查注的用户的密码和重复密码是否一致
   *
   * @param registerUserBody 注册用户对象
   */
  private void checkRepeatPassword(RegisterUserBody registerUserBody) {
    if (!registerUserBody.getPassword().equals(registerUserBody.getRepeatPassword())) {
      throw new UserException(StatusCode.USER_REPEAT_PASSWORD_ERROR);
    }
  }

  /**
   * 插入用户角色
   *
   * @param user 用户对象
   */
  private void insertUserRole(User user) {
    List<Integer> roleIds = roleMapper.selectIdsByNames(PermissionConstants.USER_ROLE_NAME);
    List<UserRole> userRoles = roleIds.stream()
        .map(roleId -> new UserRole(user.getId(), roleId))
        .collect(toCollection(() -> new ArrayList<>(roleIds.size())));
    userRoleMapper.batchInsert(userRoles);
  }

  /**
   * 将注册用户信息对象转换为用户表对应的对象 对密码进行加密
   *
   * @param registerUserBody 注册用户信息对象
   * @return 用户表对应的对象
   */
  private User convertToUser(RegisterUserBody registerUserBody) {
    return new User(registerUserBody.getUsername().trim(),
        securityUtils.encodePassword(registerUserBody.getPassword()),
        registerUserBody.getNickname(),
        true);
  }

}
