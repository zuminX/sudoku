package com.sudoku.project.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.UserException;
import com.sudoku.common.log.BusinessType;
import com.sudoku.common.log.Log;
import com.sudoku.common.tools.page.Page;
import com.sudoku.common.tools.page.PageParam;
import com.sudoku.common.tools.page.PageUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.convert.UserConvert;
import com.sudoku.project.mapper.MergeUserRoleMapper;
import com.sudoku.project.mapper.RoleMapper;
import com.sudoku.project.mapper.UserMapper;
import com.sudoku.project.model.body.AddUserBody;
import com.sudoku.project.model.body.ModifyUserBody;
import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.body.SearchUserBody;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.vo.UserDetailVO;
import com.sudoku.project.model.vo.UserVO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务层类
 */
@Service
public class UserService {

  private final UserMapper userMapper;

  private final RoleMapper roleMapper;

  private final MergeUserRoleMapper mergeUserRoleMapper;

  private final UserConvert userConvert;

  public UserService(UserMapper userMapper, RoleMapper roleMapper, MergeUserRoleMapper mergeUserRoleMapper,
      UserConvert userConvert) {
    this.userMapper = userMapper;
    this.roleMapper = roleMapper;
    this.mergeUserRoleMapper = mergeUserRoleMapper;
    this.userConvert = userConvert;
  }

  /**
   * 注册用户
   *
   * @param registerUserBody 注册用户对象
   * @return 用户显示层对象
   */
  @Transactional
  @Log(value = "注册用户", isSaveParameterData = false)
  public UserVO registerUser(RegisterUserBody registerUserBody) {
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
  public User selectWithRoleByUsername(String username) {
    return userMapper.selectWithRoleByUsername(username);
  }

  /**
   * 更新用户最近登录的时间
   *
   * @param userId 用户ID
   */
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
  @Transactional
  @Log(value = "修改用户", businessType = BusinessType.UPDATE)
  public void modifyUser(ModifyUserBody modifyUserBody) {
    checkRoleName(modifyUserBody);
    checkReUsername(modifyUserBody.getUsername(), modifyUserBody.getId());
    userMapper.updateModifyById(modifyUserBody);
    mergeUserRoleMapper.updateRoleIdByUserId(modifyUserBody.getId(), roleMapper.selectIdsByNames(modifyUserBody.getRoleNameList()));
  }

  /**
   * 新增用户
   *
   * @param addUserBody 新增用户的信息
   */
  @Transactional
  @Log(value = "新增用户", isSaveParameterData = false)
  public void addUser(AddUserBody addUserBody) {
    checkUsername(addUserBody.getUsername());
    User user = userConvert.convert(addUserBody);
    userMapper.insert(user);
    insertUserRole(user.getId(), addUserBody.getRoleNameList());
  }

  /**
   * 根据条件搜索用户
   *
   * @param searchUserBody 搜索用户的条件
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> searchUser(SearchUserBody searchUserBody) {
    searchUserBody.setUsername(StrUtil.trim(searchUserBody.getUsername()));
    searchUserBody.setNickname(StrUtil.trim(searchUserBody.getNickname()));
    return PageUtils.getPage(PageParam.<User>builder()
            .queryFunc(() -> userMapper.selectByConditionWithRole(searchUserBody))
            .page(searchUserBody.getPage())
            .pageSize(searchUserBody.getPageSize())
            .build(),
        userConvert::convertUserToUserDetailVO);
  }

  /**
   * 根据用户名或昵称搜索用户
   *
   * @param name     名称
   * @param page     当前查询页
   * @param pageSize 每页显示的条数
   * @return 用户详情的分页信息
   */
  public Page<UserDetailVO> searchUserByName(String name, Integer page, Integer pageSize) {
    return PageUtils.getPage(PageParam.<User>builder()
            .queryFunc(() -> userMapper.selectByNameWithRole(name))
            .page(page)
            .pageSize(pageSize)
            .build(),
        userConvert::convertUserToUserDetailVO);
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
   * 检查重新设置的用户名在数据库中是否已经存在
   *
   * @param username 用户名
   * @param userId   用户ID
   */
  private void checkReUsername(String username, Integer userId) {
    User user = userMapper.selectByUsername(username);
    if (user != null && !user.getId().equals(userId)) {
      throw new UserException(StatusCode.USER_HAS_EQUAL_NAME);
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
    mergeUserRoleMapper.batchInsert(userId, roleIds);
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
