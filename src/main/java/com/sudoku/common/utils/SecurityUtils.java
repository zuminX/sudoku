package com.sudoku.common.utils;

import static java.util.stream.Collectors.toList;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.project.model.entity.Role;
import com.sudoku.project.model.entity.User;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全服务工具类
 */
public class SecurityUtils {

  /**
   * 获取当前登录的用户
   *
   * @return 登录用户对象
   */
  public static LoginUserBO getLoginUser() {
    return ((LoginUserBO) getAuthentication().getPrincipal());
  }

  /**
   * 获取认证对象
   *
   * @return 认证对象
   */
  public static Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  /**
   * 获取当前用户的ID
   *
   * @return 用户ID
   */
  public static Integer getCurrentUserId() {
    return getCurrentUser().getId();
  }

  /**
   * 获取当前用户
   *
   * @return 用户对象
   */
  public static User getCurrentUser() {
    return getLoginUser().getUser();
  }

  /**
   * 判断该用户是否为管理员
   *
   * @param user 用户对象
   * @return 是管理员返回true，否则返回false
   */
  public static boolean isAdmin(User user) {
    return !isUserRolesEmpty(user) && hasAdmin(user.getRoles().stream().map(Role::getNameZh).collect(toList()));
  }

  /**
   * 判断该角色名列表是否包含管理员
   *
   * @param roleNameList 角色名列表
   * @return 包含返回true，否则返回false
   */
  public static boolean hasAdmin(@NotNull List<String> roleNameList) {
    return roleNameList.stream().anyMatch(s -> s.equals(PermissionConstants.ADMIN_NAME));
  }

  /**
   * 判断该用户的角色是否为空
   *
   * @param user 用户对象
   * @return 角色为空返回true，否则返回false
   */
  public static boolean isUserRolesEmpty(User user) {
    return user == null || CollectionUtil.isEmpty(user.getRoles());
  }

  /**
   * 判断该登录用户的角色是否为空
   *
   * @param loginUserBO 登录用户对象
   * @return 角色为空返回true，否则返回false
   */
  public static boolean isLoginUserRolesEmpty(LoginUserBO loginUserBO) {
    return loginUserBO == null || isUserRolesEmpty(loginUserBO.getUser());
  }

  /**
   * 判断该登录用户的权限是否为空
   *
   * @param loginUserBO 登录用户对象
   * @return 权限为空返回true，否则返回false
   */
  public static boolean isLoginUserPermissionsEmpty(LoginUserBO loginUserBO) {
    return loginUserBO == null || CollectionUtil.isEmpty(loginUserBO.getPermissions());
  }

  /**
   * 对密码进行加密
   *
   * @param password 原始密码
   * @return 加密后的密码
   */
  public static String encodePassword(String password) {
    return SpringUtil.getBean(PasswordEncoder.class).encode(password);
  }
}
