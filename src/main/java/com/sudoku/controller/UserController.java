package com.sudoku.controller;

import com.sudoku.model.vo.GameRecordPageVO;
import com.sudoku.model.vo.RegisterUserVO;
import com.sudoku.model.vo.UserVO;
import com.sudoku.model.vo.UserGameInformationVO;
import com.sudoku.service.GameRecordService;
import com.sudoku.service.UserGameInformationService;
import com.sudoku.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制层
 */
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

  @PostMapping("/register")
  @ApiOperation("注册用户")
  @ApiImplicitParam(name = "registerUser", value = "注册用户信息", dataTypeClass = RegisterUserVO.class, required = true)
  public UserVO registerUser(@RequestBody @Valid RegisterUserVO registerUserVO) {
    UserVO userVO = userService.registerUser(registerUserVO);
    userGameInformationService.initUserGameInformation(userVO.getId());
    return userVO;
  }

  @GetMapping("/gameInformation")
  @ApiOperation("获取用户游戏信息")
  public List<UserGameInformationVO> getUserGameInformation() {
    return userGameInformationService.getUserGameInformation();
  }

  @GetMapping("/historyGameRecord")
  @ApiOperation("获取历史游戏记录")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "page", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "pageSize", dataTypeClass = Integer.class, required = true)
  })
  public GameRecordPageVO getHistoryGameRecord(@RequestParam("page") Integer page,
      @RequestParam("pageSize") @Range(min = 1, max = 20, message = "每页显示的记录数在1-20条之间") Integer pageSize) {
    return gameRecordService.getHistoryGameRecord(page, pageSize);
  }
}
