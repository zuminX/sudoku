package com.sudoku.service.impl;

import com.sudoku.constant.consist.RoleName;
import com.sudoku.constant.enums.StatusCode;
import com.sudoku.convert.UserConvert;
import com.sudoku.exception.UserException;
import com.sudoku.mapper.RoleMapper;
import com.sudoku.mapper.UserMapper;
import com.sudoku.mapper.UserRoleMapper;
import com.sudoku.model.po.User;
import com.sudoku.model.po.UserRole;
import com.sudoku.model.vo.RegisterUserVO;
import com.sudoku.model.vo.UserVO;
import com.sudoku.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  /**
   * 注册用户
   *
   * @param registerUserVO 注册用户对象
   * @return 用户显示层对象
   */
  @Override
  @Transactional
  public UserVO registerUser(RegisterUserVO registerUserVO) {
    if (!registerUserVO.getPassword().equals(registerUserVO.getRepeatPassword())) {
      throw new UserException(StatusCode.USER_REPEAT_PASSWORD_ERROR);
    }
    User user = UserConvert.INSTANCE.convert(registerUserVO);
    //若已经有相同用户名的用户
    if (userMapper.findByUsername(user.getUsername()) != null) {
      throw new UserException(StatusCode.USER_HAS_EQUAL_NAME);
    }
    //新增用户信息
    userMapper.insert(user);
    //新增用户与角色管理
    insertUserRole(user);
    return UserConvert.INSTANCE.convert(user);
  }

  /**
   * 插入用户角色
   *
   * @param user 用户对象
   */
  private void insertUserRole(User user) {
    List<Integer> roleIds = roleMapper.selectIdsByNames(RoleName.USER);
    List<UserRole> userRoles = roleIds.stream().map(roleId -> new UserRole(user.getId(), roleId))
        .collect(Collectors.toCollection(() -> new ArrayList<>(roleIds.size())));
    userRoleMapper.batchInsert(userRoles);
  }
}
