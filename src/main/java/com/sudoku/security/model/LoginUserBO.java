package com.sudoku.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sudoku.model.entity.User;
import java.util.Collection;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录用户业务类
 */
@Data
@NoArgsConstructor
public class LoginUserBO implements UserDetails {

  private static final long serialVersionUID = -2436545453049796344L;

  /**
   * 用户唯一标识
   */
  private String uuid;

  /**
   * token过期时间
   */
  private Long expireTime;

  /**
   * 用户信息
   */
  private User user;

  /**
   * 权限集合
   */
  private Set<String> permissions;

  /**
   * 构造方法
   *
   * @param user        用户对象
   * @param permissions 权限列表
   */
  public LoginUserBO(User user, Set<String> permissions) {
    this.user = user;
    this.permissions = permissions;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
