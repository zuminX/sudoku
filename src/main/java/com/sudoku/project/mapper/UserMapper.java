package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

  /**
   * 根据用户名查找用户
   *
   * @param username 用户名
   * @return 用户对象
   */
  User selectByUsername(@Param("username") String username);

  /**
   * 根据用户名查询带有角色信息的用户对象
   *
   * @param username 用户名
   * @return 用户对象
   */
  User selectWithRoleByUsername(@Param("username") String username);

  /**
   * 更新指定ID的用户的最近登录时间
   *
   * @param loginTime 登录时间
   * @param id        用户ID
   * @return 更新的行数
   */
  int updateRecentLoginTimeById(@Param("loginTime") LocalDateTime loginTime, @Param("id") Integer id);

  /**
   * 统计在[startCreateTime,endCreateTime)天中创建的用户数
   *
   * @param startCreateTime 起始创建时间
   * @param endCreateTime   终止创建时间
   * @return 用户数
   */
  Integer countByCreateTimeBetween(@Param("startCreateTime") LocalDate startCreateTime, @Param("endCreateTime") LocalDate endCreateTime);

  /**
   * 统计在[startLoginTime,endLoginTime)天中登录的用户数
   *
   * @param startLoginTime 起始登录时间
   * @param endLoginTime   终止登录时间
   * @return 用户数
   */
  Integer countByRecentLoginTimeBetween(@Param("startLoginTime") LocalDate startLoginTime, @Param("endLoginTime") LocalDate endLoginTime);

  /**
   * 查找系统用户中最早注册的时间
   *
   * @return 最早注册的时间
   */
  LocalDateTime findFirstCreateTimeOrderByCreateTime();
}