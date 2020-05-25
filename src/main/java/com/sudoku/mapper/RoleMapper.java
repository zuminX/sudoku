package com.sudoku.mapper;

import com.sudoku.model.po.Role;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

/**
 * 角色持久层
 */
public interface RoleMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Role record);

  int insertSelective(Role record);

  Role selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Role record);

  int updateByPrimaryKey(Role record);

  @Cacheable(cacheNames = "roles", key = "#names")
  List<Integer> selectIdsByNames(List<String> names);

}