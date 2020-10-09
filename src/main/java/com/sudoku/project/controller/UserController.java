package com.sudoku.project.controller;

import com.sudoku.common.tools.page.Page;
import com.sudoku.framework.security.service.CaptchaService;
import com.sudoku.project.model.body.AddUserBody;
import com.sudoku.project.model.body.ModifyUserBody;
import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.body.SearchUserBody;
import com.sudoku.project.model.vo.GameRecordVO;
import com.sudoku.project.model.vo.UserDetailVO;
import com.sudoku.project.model.vo.UserGameInformationVO;
import com.sudoku.project.model.vo.UserVO;
import com.sudoku.project.service.GameRecordService;
import com.sudoku.project.service.UserGameInformationService;
import com.sudoku.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Range;
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
  private final UserGameInformationService userGameInformationService;
  private final GameRecordService gameRecordService;
  private final CaptchaService captchaService;

  public UserController(UserService userService,
      UserGameInformationService userGameInformationService, GameRecordService gameRecordService, CaptchaService captchaService) {
    this.userService = userService;
    this.userGameInformationService = userGameInformationService;
    this.gameRecordService = gameRecordService;
    this.captchaService = captchaService;
  }

  @PostMapping("/register")
  @ApiOperation("注册用户")
  @ApiImplicitParam(name = "registerUser", value = "注册用户信息", dataTypeClass = RegisterUserBody.class, required = true)
  public UserVO registerUser(@RequestBody @Valid RegisterUserBody registerUser) {
    captchaService.checkCaptcha(registerUser.getUuid(), registerUser.getCode());
    UserVO userVO = userService.registerUser(registerUser);
    userGameInformationService.initUserGameInformation(userVO.getId());
    return userVO;
  }

  @GetMapping("/gameInformation")
  @PreAuthorize("@ss.hasPermission('sudoku:user:information')")
  @ApiOperation("获取用户游戏信息")
  public List<UserGameInformationVO> getUserGameInformation() {
    return userGameInformationService.getUserGameInformation();
  }

  @GetMapping("/historyGameRecord")
  @PreAuthorize("@ss.hasPermission('sudoku:user:record')")
  @ApiOperation("获取历史游戏记录")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<GameRecordVO> getHistoryGameRecord(@RequestParam Integer page,
      @RequestParam @Range(min = 1, max = 20, message = "每页显示的记录数在1-20条之间") Integer pageSize) {
    return gameRecordService.getHistoryGameRecord(page, pageSize);
  }

  @GetMapping("/userList")
  @PreAuthorize("@ss.hasPermission('system:user:list')")
  @ApiOperation("获取用户列表")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<UserDetailVO> getUserList(@RequestParam Integer page,
      @RequestParam @Range(min = 1, max = 20, message = "每页显示的用户数在1-20个之间") Integer pageSize) {
    return userService.getUserList(page, pageSize);
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
  @ApiImplicitParams({
      @ApiImplicitParam(name = "username", value = "名称", dataTypeClass = String.class, required = true),
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<UserDetailVO> searchUserByName(@RequestParam String name,
      @RequestParam Integer page,
      @RequestParam @Range(min = 1, max = 20, message = "每页显示的用户数在1-20个之间") Integer pageSize) {
    return userService.searchUserByName(name, page, pageSize);
  }
}
