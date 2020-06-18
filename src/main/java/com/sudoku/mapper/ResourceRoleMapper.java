package com.sudoku.mapper;

import com.sudoku.model.entity.ResourceRole;

/**
 * 资源角色持久层
 */
public interface ResourceRoleMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(ResourceRole record);

  int insertSelective(ResourceRole record);

  ResourceRole selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(ResourceRole record);

  int updateByPrimaryKey(ResourceRole record);
}