package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.Resource;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface ResourceMapper extends BaseMapper<Resource> {

  @Cacheable(cacheNames = "resources")
  List<Resource> selectAllResourcesWithRole();
}