package com.sudoku.game.controller;

import com.sudoku.game.model.bo.SudokuDataBO;
import com.sudoku.game.model.body.RaceInformationBody;
import com.sudoku.game.model.vo.RaceInformationVO;
import com.sudoku.game.service.RaceInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@RequestMapping("/gameRace")
@Validated
@Api(tags = "数独游戏竞赛API接口")
public class GameRaceController extends GameBaseController {

  private final RaceInformationService raceInformationService;

  public GameRaceController(RaceInformationService raceInformationService) {
    this.raceInformationService = raceInformationService;
  }

  @PostMapping("/publishPublicRace")
  @PreAuthorize("@ss.hasPermission('sudoku:race:add')")
  @ApiOperation("发布公开数独游戏竞赛")
  @ApiImplicitParam(name = "raceInformationBody", value = "竞赛内容信息体类", dataTypeClass = RaceInformationBody.class, required = true)
  public void publishPublicRace(@Valid @RequestBody RaceInformationBody raceInformationBody) {
    raceInformationService.addPublicRace(raceInformationBody);
  }

  @GetMapping("/publicRaceList")
  @ApiOperation("获取公开的数独游戏竞赛")
  public List<RaceInformationVO> getPublicRaceList() {
    return raceInformationService.getPublicRaceList();
  }

  @PostMapping("/joinPublicRace")
  @ApiOperation("参加公开数独游戏竞赛")
  @ApiImplicitParam(name = "raceId", value = "竞赛ID", dataTypeClass = Integer.class, required = true)
  public SudokuDataBO joinPublicRace(@RequestParam Integer raceId) {
    return null;
  }
}
