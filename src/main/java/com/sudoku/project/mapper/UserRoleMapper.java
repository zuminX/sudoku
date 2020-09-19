package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.UserRole;
import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

  int batchInsert(List<UserRole> userRoles);
}