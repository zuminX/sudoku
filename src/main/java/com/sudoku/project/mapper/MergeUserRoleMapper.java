package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.MergeUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户角色持久层类
 */
public interface MergeUserRoleMapper extends BaseMapper<MergeUserRole> {

  /**
   * 批量插入指定用户ID的角色
   *
   * @param userId     用户ID
   * @param roleIdList 角色ID列表
   * @return 影响行数
   */
  int batchInsert(@Param("userId") Integer userId, @Param("roleIdList") List<Integer> roleIdList);

  /**
   * 根据用户ID插入用户角色
   *
   * @param userId 用户ID
   * @return 影响行数
   */
  int deleteByUserId(@Param("userId") Integer userId);

  /**
   * 根据用户ID更新其角色
   *
   * @param userId     用户ID
   * @param roleIdList 角色ID列表
   */
  default void updateRoleIdByUserId(Integer userId, List<Integer> roleIdList) {
    deleteByUserId(userId);
    batchInsert(userId, roleIdList);
  }
}