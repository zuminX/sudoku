package com.sudoku.system.controller;

import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.UserException;
import com.sudoku.common.tools.page.Page;
import com.sudoku.system.model.body.AddUserBody;
import com.sudoku.system.model.body.ModifyUserBody;
import com.sudoku.system.model.body.RegisterUserBody;
import com.sudoku.system.model.body.SearchUserBody;
import com.sudoku.system.model.vo.UserDetailVO;
import com.sudoku.system.model.vo.UserVO;
import com.sudoku.system.service.CaptchaService;
import com.sudoku.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
@Api(tags = "用户API接口")
public class UserController {

  private final UserService userService;

  private final CaptchaService captchaService;

  public UserController(UserService userService, CaptchaService captchaService) {
    this.userService = userService;
    this.captchaService = captchaService;
  }

  @PostMapping("/register")
  @ApiOperation("注册用户")
  @ApiImplicitParam(name = "registerUser", value = "注册用户信息", dataTypeClass = RegisterUserBody.class, required = true)
  public UserVO registerUser(@RequestBody @Valid RegisterUserBody registerUser) {
    captchaService.checkCaptcha(registerUser.getUuid(), registerUser.getCode());
    checkRepeatPassword(registerUser);
    return userService.registerUser(registerUser);
  }

  @GetMapping("/userList")
  @PreAuthorize("@ss.hasPermission('system:user:list')")
  @ApiOperation("获取用户列表")
  public Page<UserDetailVO> getUserList() {
    return userService.getUserList();
  }

  @PostMapping("/modifyUser")
  @PreAuthorize("@ss.hasPermission('system:user:modify')")
  @ApiOperation("修改用户信息")
  @ApiImplicitParam(name = "modifyUserBody", value = "修改的用户信息", dataTypeClass = ModifyUserBody.class, required = true)
  public void modifyUser(@RequestBody @Valid ModifyUserBody modifyUserBody) {
    userService.modifyUser(modifyUserBody);
  }

  @PostMapping("/addUser")
  @PreAuthorize("@ss.hasPermission('system:user:add')")
  @ApiOperation("新增用户")
  @ApiImplicitParam(name = "addUserBody", value = "新增用户的信息", dataTypeClass = AddUserBody.class, required = true)
  public void addUser(@RequestBody @Valid AddUserBody addUserBody) {
    userService.addUser(addUserBody);
  }

  @PostMapping("/searchUser")
  @PreAuthorize("@ss.hasPermission('system:user:search')")
  @ApiOperation("根据条件搜索用户")
  @ApiImplicitParam(name = "searchUserBody", value = "搜索用户的条件", dataTypeClass = SearchUserBody.class, required = true)
  public Page<UserDetailVO> searchUser(@RequestBody @Valid SearchUserBody searchUserBody) {
    return userService.searchUser(searchUserBody);
  }

  @GetMapping("/searchUserByName")
  @PreAuthorize("@ss.hasPermission('system:user:search')")
  @ApiOperation("根据名称搜索用户")
  @ApiImplicitParam(name = "username", value = "名称", dataTypeClass = String.class, required = true)
  public Page<UserDetailVO> searchUserByName(@RequestParam String name) {
    return userService.searchUserByName(name);
  }

  /**
   * 检查注册用户的密码和重复密码是否一致
   *
   * @param registerUserBody 注册用户对象
   */
  private void checkRepeatPassword(RegisterUserBody registerUserBody) {
    if (!registerUserBody.getPassword().equals(registerUserBody.getRepeatPassword())) {
      throw new UserException(StatusCode.USER_REPEAT_PASSWORD_ERROR);
    }
  }
}
