package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.Role;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface RoleMapper extends BaseMapper<Role> {

  List<Role> selectByUserId(Integer id);

  @Cacheable(value = "rolesByNames", keyGenerator = "simpleKG")
  List<Integer> selectIdsByNames(List<String> names);
}