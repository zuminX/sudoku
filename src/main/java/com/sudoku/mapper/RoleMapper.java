package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.Role;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface RoleMapper extends BaseMapper<Role> {

  List<Role> selectByUserId(Integer id);

  @Cacheable(cacheNames = "roles", key = "#names")
  List<Integer> selectIdsByNames(List<String> names);
}