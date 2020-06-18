package com.sudoku.config.security;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * 自定义访问管理器
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

  /**
   * 决定当前用户是否具有对应的权限访问该地址
   *
   * @param authentication   当前登录用户的角色信息
   * @param object           访问对象
   * @param configAttributes 当前请求需要的角色
   */
  @Override
  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) {
    for (ConfigAttribute configAttribute : configAttributes) {
      //当前请求需要的角色
      String needRole = configAttribute.getAttribute();
      //需要登陆
      if ("ROLE_LOGIN".equals(needRole)) {
        //用户没有登录
        if (authentication instanceof AnonymousAuthenticationToken) {
          throw new AccessDeniedException("尚未登录，请登录!");
          //已经登录，放行
        } else {
          return;
        }
      }
      //获取当前角色具有的角色
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      for (GrantedAuthority authority : authorities) {
        //该角色为当前所需的角色
        if (authority.getAuthority().equals(needRole)) {
          return;
        }
      }
    }
    throw new AccessDeniedException("权限不足，请联系管理员!");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
