package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.MergeUserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MergeUserRoleMapper extends BaseMapper<MergeUserRole> {

  int batchInsert(@Param("userId") Integer userId, @Param("roleIdList") List<Integer> roleIdList);

  int deleteByUserId(@Param("userId") Integer userId);

  default void updateRoleIdByUserId(Integer userId, List<Integer> roleIdList) {
    deleteByUserId(userId);
    batchInsert(userId, roleIdList);
  }
}