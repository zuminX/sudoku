package com.sudoku.system.controller;

import com.sudoku.common.core.controller.BaseController;
import com.sudoku.system.model.vo.RoleVO;
import com.sudoku.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Api(tags = "系统角色API接口")
public class RoleController extends BaseController {

  private final RoleService roleService;

  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping("/roleList")
  @PreAuthorize("@ss.hasPermission('system:role:list')")
  @ApiOperation("获取系统角色名列表")
  public List<RoleVO> getRoleList() {
    return roleService.getRoleList();
  }
}
