package com.sudoku.mapper;

import com.sudoku.model.po.Role;
import com.sudoku.model.po.User;
import java.util.List;

/**
 * 用户持久层
 */
public interface UserMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(User record);

  int insertSelective(User record);

  User selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(User record);

  int updateByPrimaryKey(User record);

  User findByUsername(String username);

  List<Role> getUserRolesById(Integer id);

}