package com.sudoku.mapper;

import com.sudoku.model.entity.UserRole;
import java.util.List;

/**
 * 用户角色持久层
 */
public interface UserRoleMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(UserRole record);

  int insertSelective(UserRole record);

  UserRole selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(UserRole record);

  int updateByPrimaryKey(UserRole record);

  int batchInsert(List<UserRole> userRoles);
}