package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.Resource;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * 资源持久层类
 */
public interface ResourceMapper extends BaseMapper<Resource> {

  /**
   * 根据用户ID查询其权限名集合
   *
   * @param userId 用户ID
   * @return 权限名集合
   */
  Set<String> selectPermsByUserId(@Param("userId") Integer userId);
}