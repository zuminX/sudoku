package com.sudoku.project.convert;

import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.model.body.AddUserBody;
import com.sudoku.project.model.entity.Role;
import com.sudoku.project.model.entity.User;
import com.sudoku.project.model.vo.RoleVO;
import com.sudoku.project.model.vo.UserDetailVO;
import com.sudoku.project.model.vo.UserVO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 用户转换器
 */
@Mapper(imports = SecurityUtils.class)
public interface UserConvert {

  /**
   * 将用户表对应的对象转换为显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户显示层对象
   */
  @Mapping(target = "roleList", source = "roles")
  UserVO convert(User user);

  /**
   * 将角色表对应的对象列表转化为角色显示层对象列表
   *
   * @param roleList 角色表对应的对象列表
   * @return 角色显示层对象列表
   */
  List<RoleVO> convertRoleListToRoleVOList(List<Role> roleList);

  /**
   * 将新增用户对象转化为用户表对应的对象
   *
   * @param addUserBody 新增用户对象
   * @return 用户表对应的对象
   */
  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "roles", ignore = true),
      @Mapping(target = "password", expression = "java(SecurityUtils.encodePassword(addUserBody.getPassword()))")
  })
  User convert(AddUserBody addUserBody);

  /**
   * 将用户表对应的对象转换为用户详情显示层对象
   *
   * @param user 用户表对应的对象
   * @return 用户详情显示层对象
   */
  @Mapping(target = "roleNameList", source = "roles")
  UserDetailVO convertUserToUserDetailVO(User user);

  /**
   * 自定义角色列表属性的转换
   *
   * @param roleList 角色列表
   * @return 角色名列表
   */
  default List<String> roleListToRoleNameList(List<Role> roleList) {
    return roleList == null ? null : roleList.stream().map(Role::getNameZh).collect(Collectors.toList());
  }
}
