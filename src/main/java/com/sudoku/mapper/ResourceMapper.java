package com.sudoku.mapper;

import com.sudoku.model.entity.Resource;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

/**
 * 资源持久层
 */
public interface ResourceMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(Resource record);

  int insertSelective(Resource record);

  Resource selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Resource record);

  int updateByPrimaryKey(Resource record);

  @Cacheable(cacheNames = "resources")
  List<Resource> selectAllResourcesWithRole();
}