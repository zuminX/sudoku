package com.sudoku.game.controller;

import com.sudoku.common.tools.page.Page;
import com.sudoku.game.model.vo.NormalGameRecordVO;
import com.sudoku.game.model.vo.UserGameInformationVO;
import com.sudoku.game.service.NormalGameRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
@Api(tags = "用户API接口")
public class UserGameController extends GameBaseController {

  private final NormalGameRecordService normalGameRecordService;

  public UserGameController(NormalGameRecordService normalGameRecordService) {
    this.normalGameRecordService = normalGameRecordService;
  }

  @GetMapping("/gameInformation")
  @ApiOperation("获取用户游戏信息")
  public List<UserGameInformationVO> getUserGameInformation() {
    return normalGameRecordService.getUserGameInformation();
  }

  @GetMapping("/historyGameRecord")
  @ApiOperation("获取历史游戏记录")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<NormalGameRecordVO> getHistoryGameRecord(@RequestParam Integer page,
      @RequestParam @Range(min = 1, max = 20, message = "每页显示的记录数在1-20条之间") Integer pageSize) {
    return normalGameRecordService.getHistoryGameRecord(page, pageSize);
  }

  @GetMapping("/gameInformationById")
  @PreAuthorize("@ss.hasPermission('sudoku:user:information')")
  @ApiOperation("根据用户ID，获取其游戏信息")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Integer.class, required = true),
  })
  public List<UserGameInformationVO> getUserGameInformationById(@RequestParam Integer userId) {
    return normalGameRecordService.getUserGameInformation(userId);
  }

  @GetMapping("/historyGameRecordById")
  @PreAuthorize("@ss.hasPermission('sudoku:user:record')")
  @ApiOperation("根据用户ID，获取其历史游戏记录")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<NormalGameRecordVO> getHistoryGameRecordById(@RequestParam Integer userId, @RequestParam Integer page,
      @RequestParam @Range(min = 1, max = 20, message = "每页显示的记录数在1-20条之间") Integer pageSize) {
    return normalGameRecordService.getHistoryGameRecordById(userId, page, pageSize);
  }

}

