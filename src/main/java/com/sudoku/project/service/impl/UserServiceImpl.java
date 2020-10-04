package com.sudoku.project.service.impl;

import static java.util.stream.Collectors.toCollection;

import cn.hutool.core.collection.CollUtil;
import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.UserException;
import com.sudoku.common.log.Log;
import com.sudoku.common.tools.page.Page;
import com.sudoku.common.tools.page.PageParam;
import com.sudoku.common.tools.page.PageUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.convert.UserConvert;
import com.sudoku.project.mapper.RoleMapper;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.mapper.UserRoleMapper;
import com.sudoku.project.model.body.AddUserBody;
import com.sudoku.project.model.body.ModifyUserBody;
import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.entity.UserRole;
import com.sudoku.project.model.vo.UserDetailVO;
import com.sudoku.project.model.vo.UserVO;
import com.sudoku.project.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务层实现类
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final RoleMapper roleMapper;
  private final UserRoleMapper userRoleMapper;
  private final UserConvert userConvert;

  public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper,
       UserConvert userConvert) {
    this.userMapper = userMapper;
    this.roleMapper = roleMapper;
    this.userRoleMapper = userRoleMapper;
    this.userConvert = userConvert;
  }

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
    checkUsername(user.getUsername());
    userMapper.insert(user);
    insertUserRole(user.getId(), PermissionConstants.USER_ROLE_NAME);
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
   * 获取系统用户列表
   *
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 用户详情的分页信息
   */
  @Override
  public Page<UserDetailVO> getUserList(Integer page, Integer pageSize) {
    return PageUtils.getPage(PageParam.<User>builder()
            .queryFunc(userMapper::selectAllWithRole)
            .page(page)
            .pageSize(pageSize)
            .build(),
        userConvert::convertUserToUserDetailVO);
  }

  /**
   * 修改用户信息
   *
   * @param modifyUserBody 修改的用户信息
   */
  @Override
  public void modifyUser(ModifyUserBody modifyUserBody) {
    checkRoleName(modifyUserBody);
    checkUsername(modifyUserBody.getUsername());
    userMapper.updateModifyById(modifyUserBody);
    userRoleMapper.updateRoleIdByUserId(modifyUserBody.getId(), roleMapper.selectIdsByNames(modifyUserBody.getRoleNameList()));
  }

  /**
   * 新增用户
   *
   * @param addUserBody 新增用户的信息
   */
  @Override
  public void addUser(AddUserBody addUserBody) {
    checkUsername(addUserBody.getUsername());
    User user = userConvert.convert(addUserBody);
    userMapper.insert(user);
    insertUserRole(user.getId(), addUserBody.getRoleNameList());
  }

  /**
   * 检查待修改的用户的角色名列表是否为空或管理员。若是，则抛出用户异常
   *
   * @param modifyUserBody 修改的用户对象
   */
  private void checkRoleName(ModifyUserBody modifyUserBody) {
    List<String> roleNameList = roleMapper.selectNameZhByUserId(modifyUserBody.getId());
    if (CollUtil.isEmpty(roleNameList)) {
      throw new UserException(StatusCode.USER_NOT_FOUND);
    }
    if (SecurityUtils.hasAdmin(roleNameList)) {
      throw new UserException(StatusCode.USER_NOT_MODIFY_AUTHORITY);
    }
  }

  /**
   * 检查指定的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   */
  private void checkUsername(String username) {
    if (userMapper.selectByUsername(username) != null) {
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
   * @param userId       用户ID
   * @param roleNameList 角色名列表
   */
  private void insertUserRole(Integer userId, List<String> roleNameList) {
    List<Integer> roleIds = roleMapper.selectIdsByNames(roleNameList);
    List<UserRole> userRoles = roleIds.stream()
        .map(roleId -> new UserRole(userId, roleId))
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
        SecurityUtils.encodePassword(registerUserBody.getPassword()),
        registerUserBody.getNickname(),
        true);
  }

}
