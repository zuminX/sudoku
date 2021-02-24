package com.sudoku.system.service;

import com.sudoku.system.mapper.RoleMapper;
import com.sudoku.system.model.vo.RoleVO;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 角色业务层类
 */
@Service
public class RoleService {

  private final RoleMapper roleMapper;

  public RoleService(RoleMapper roleMapper) {
    this.roleMapper = roleMapper;
  }

  /**
   * 获取系统中所有角色
   *
   * @return 角色列表
   */
  public List<RoleVO> getRoleList() {
    return roleMapper.selectNameAndNameZh();
  }
}
