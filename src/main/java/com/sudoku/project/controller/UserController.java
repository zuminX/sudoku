package com.sudoku.project.controller;

import com.sudoku.project.model.body.RegisterUserBody;
import com.sudoku.project.model.vo.GameRecordVO;
import com.sudoku.project.model.vo.PageVO;
import com.sudoku.project.model.vo.UserGameInformationVO;
import com.sudoku.project.model.vo.UserVO;
import com.sudoku.framework.security.service.CaptchaService;
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
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private UserService userService;
  @Autowired
  private UserGameInformationService userGameInformationService;
  @Autowired
  private GameRecordService gameRecordService;
  @Autowired
  private CaptchaService captchaService;

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
  public PageVO<GameRecordVO> getHistoryGameRecord(@RequestParam Integer page,
      @RequestParam @Range(min = 1, max = 20, message = "每页显示的记录数在1-20条之间") Integer pageSize) {
    return gameRecordService.getHistoryGameRecord(page, pageSize);
  }
}
