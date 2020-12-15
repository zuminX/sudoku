package utils;

import com.sudoku.common.constant.consist.PermissionConstants;
import com.sudoku.framework.security.model.LoginUserBO;
import com.sudoku.project.model.entity.Role;
import com.sudoku.project.model.entity.User;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class UserMockUtils {

  private UserMockUtils() {
  }

  public static User getAdminUser() {
    return getUser(PermissionConstants.ADMIN_ROLE_NAME);
  }

  public static User getNormalUser() {
    return getUser(PermissionConstants.USER_ROLE_NAME);
  }

  public static LoginUserBO getAdminLoginUser() {
    return getLoginUser(getAdminUser(), Collections.singleton(PermissionConstants.ADMIN_PERMISSION));
  }

  public static LoginUserBO getAdminLoginUser(Set<String> permissions) {
    permissions.add(PermissionConstants.ADMIN_PERMISSION);
    return getLoginUser(getAdminUser(), permissions);
  }

  public static LoginUserBO getNormalLoginUser(Set<String> permissions) {
    return getLoginUser(getNormalUser(), permissions);
  }

  private static User getUser(List<String> adminRoleName) {
    User user = new User();
    user.setRoles(getRoleList(adminRoleName));
    return user;
  }

  private static LoginUserBO getLoginUser(User user, Set<String> singleton) {
    LoginUserBO loginUser = new LoginUserBO();
    loginUser.setUser(user);
    loginUser.setPermissions(singleton);
    return loginUser;
  }

  /**
   * 根据角色名列表模拟生成角色列表
   *
   * @param roleNameList 角色名列表
   * @return 角色列表
   */
  private static List<Role> getRoleList(@NotNull List<String> roleNameList) {
    return roleNameList.stream()
        .map(roleName -> new Role(1, roleName, roleName))
        .collect(Collectors.toList());
  }
}
