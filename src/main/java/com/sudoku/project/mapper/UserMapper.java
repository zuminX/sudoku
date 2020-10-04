package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.body.ModifyUserBody;
import com.sudoku.project.model.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
   * 查找系统所有用户
   *
   * @return 用户列表
   */
  List<User> selectAll();

  /**
   * 查找系统所有用户且用户带有角色信息
   *
   * @return 用户列表
   */
  List<User> selectAllWithRole();

  /**
   * 根据ID更新用户
   *
   * @param user 用户修改对象
   * @return 更新的行数
   */
  int updateModifyById(@Param("user") ModifyUserBody user);

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
  Optional<Integer> countByCreateTimeBetween(@Param("startCreateTime") LocalDate startCreateTime,
      @Param("endCreateTime") LocalDate endCreateTime);

  /**
   * 统计在[startLoginTime,endLoginTime)天中登录的用户数
   *
   * @param startLoginTime 起始登录时间
   * @param endLoginTime   终止登录时间
   * @return 用户数
   */
  Optional<Integer> countByRecentLoginTimeBetween(@Param("startLoginTime") LocalDate startLoginTime,
      @Param("endLoginTime") LocalDate endLoginTime);

  /**
   * 查找系统用户中最早注册的时间
   *
   * @return 最早注册的时间
   */
  LocalDateTime findFirstCreateTimeOrderByCreateTime();
}