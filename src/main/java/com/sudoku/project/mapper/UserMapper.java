package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.User;
import java.time.LocalDateTime;
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

  int updateRecentLoginTimeById(@Param("updatedRecentLoginTime") LocalDateTime updatedRecentLoginTime, @Param("id") Integer id);

  /**
   * 统计在[startCreateTime,endCreateTime)天中创建的用户数
   *
   * @param startCreateTime 起始创建时间
   * @param endCreateTime   终止创建时间
   * @return 用户数
   */
  Integer countByCreateTimeBetween(@Param("startCreateTime") LocalDateTime startCreateTime,
      @Param("endCreateTime") LocalDateTime endCreateTime);

  /**
   * 统计在[startLoginTime,endLoginTime)天中登录的用户数
   *
   * @param startLoginTime 起始登录时间
   * @param endLoginTime   终止登录时间
   * @return 用户数
   */
  Integer countByRecentLoginTimeBetween(@Param("startLoginTime") LocalDateTime startLoginTime,
      @Param("endLoginTime") LocalDateTime endLoginTime);

  /**
   * 查找系统用户中最早注册的时间
   *
   * @return 最早注册的时间
   */
  LocalDateTime findFirstCreateTimeOrderByCreateTime();
}