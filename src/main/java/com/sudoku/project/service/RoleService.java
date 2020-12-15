package com.sudoku.project.service;

import com.sudoku.project.mapper.RoleMapper;
import com.sudoku.project.model.vo.RoleVO;
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

  public List<RoleVO> getRoleList() {
    return roleMapper.selectNameAndNameZh();
  }
}
