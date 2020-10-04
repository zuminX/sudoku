package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {

  int batchInsert(@Param("userRoles") List<UserRole> userRoles);

  int updateRoleIdByUserId(@Param("userId") Integer userId, @Param("roleIdList") List<Integer> roleIdList);
}