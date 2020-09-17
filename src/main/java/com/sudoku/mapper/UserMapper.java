package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.User;
import java.util.Date;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

  User selectByUsername(@Param("username") String username);

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  User selectWithRoleByUsername(@Param("username") String username);

  int updateRecentLoginTimeById(@Param("updatedRecentLoginTime") Date updatedRecentLoginTime, @Param("id") Integer id);
}