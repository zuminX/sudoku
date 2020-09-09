package com.sudoku.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.sudoku.constant.consist.PermissionConstants;
import com.sudoku.model.entity.User;
import com.sudoku.security.model.LoginUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 安全服务工具类
 */
@Component
public class SecurityUtils {

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * 获取密码编码器
   *
   * @return BCrypt密码编码器
   */
  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

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
   * 获取当前登录的用户的ID
   *
   * @return 用户ID
   */
  public static Integer getUserId() {
    return getLoginUser().getUser().getId();
  }

  /**
   * 判断该用户是否为管理员
   *
   * @param user 用户对象
   * @return 是管理员返回true，否则返回false
   */
  public static boolean isAdmin(User user) {
    if (isUserRolesEmpty(user)) {
      return false;
    }
    return user.getRoles().stream().anyMatch(r -> r.getName().equals(PermissionConstants.ADMIN_NAME));
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
  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
}
