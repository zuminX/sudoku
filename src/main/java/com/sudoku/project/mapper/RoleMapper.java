package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.Role;
import com.sudoku.project.model.vo.RoleVO;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

/**
 * 角色持久层类
 */
public interface RoleMapper extends BaseMapper<Role> {

  /**
   * 查询所有的角色名和角色名称
   *
   * @return 角色显示层对象列表
   */
  @Cacheable(value = "roleVoList", keyGenerator = "simpleKG")
  List<RoleVO> selectNameAndNameZh();

  /**
   * 根据用户ID查询角色名
   *
   * @param id 用户ID
   * @return 角色名列表
   */
  List<String> selectNameZhByUserId(Integer id);

  /**
   * 根据角色名列表查询对应的角色ID列表
   *
   * @param names 角色名列表
   * @return 角色ID列表
   */
  @Cacheable(value = "rolesByNames", keyGenerator = "simpleKG")
  List<Integer> selectIdsByNames(List<String> names);
}