package com.sudoku.project.service.impl;

import com.sudoku.project.mapper.RoleMapper;
import com.sudoku.project.model.vo.RoleVO;
import com.sudoku.project.service.RoleService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleMapper roleMapper;

  public RoleServiceImpl(RoleMapper roleMapper) {
    this.roleMapper = roleMapper;
  }

  @Override
  public List<RoleVO> getRoleList() {
    return roleMapper.selectNameAndNameZh();
  }
}
