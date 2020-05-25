package com.sudoku.config;

import com.sudoku.model.po.Resource;
import com.sudoku.model.po.Role;
import com.sudoku.service.ResourceService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * 过滤请求类
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  /**
   * 路径匹配
   */
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Autowired
  private ResourceService resourceService;

  /**
   * 通过当前的请求地址，获取该地址需要的用户角色
   *
   * @param object 访问对象
   * @return 角色集合
   * @throws IllegalArgumentException 参数不合法异常
   */
  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    //获取请求地址
    String requestUrl = ((FilterInvocation) object).getRequestUrl();
    //查询数据库中url pattern和role的对应关系
    List<Resource> resources = resourceService.getAllResourcesWithRole();
    for (Resource resource : resources) {
      //请求地址与匹配规则是否对应
      if (antPathMatcher.match(resource.getUrl(), requestUrl)) {
        //获取对应的角色列表
        List<Role> roles = resource.getRoles();
        //获取角色名
        String[] str = roles.stream().map(Role::getName).toArray(String[]::new);
        //创建角色集合
        return SecurityConfig.createList(str);
      }
    }
    //没有匹配上的，都需要登陆访问
    return SecurityConfig.createList("ROLE_LOGIN");
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
