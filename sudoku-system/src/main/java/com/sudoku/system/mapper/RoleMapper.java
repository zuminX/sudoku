package com.sudoku.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.common.annotation.ExtCacheable;
import com.sudoku.system.model.entity.Role;
import com.sudoku.system.model.vo.RoleVO;
import java.util.List;

/**
 * 角色持久层类
 */
public interface RoleMapper extends BaseMapper<Role> {

  /**
   * 查询所有的角色名和角色名称
   *
   * @return 角色显示层对象列表
   */
  @ExtCacheable(value = "roleVoList")
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
  @ExtCacheable(value = "rolesByNames")
  List<Integer> selectIdsByNames(List<String> names);
}