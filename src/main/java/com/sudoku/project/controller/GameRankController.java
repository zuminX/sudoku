package com.sudoku.project.controller;

import com.sudoku.common.constant.enums.RankingType;
import com.sudoku.common.tools.page.Page;
import com.sudoku.project.model.bo.RankItemDataBO;
import com.sudoku.project.service.GameRankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gameRank")
@Validated
@Api(tags = "数独游戏排行API接口")
public class GameRankController extends BaseController {

  private final GameRankService gameRankService;

  public GameRankController(GameRankService gameRankService) {
    this.gameRankService = gameRankService;
  }

  @GetMapping("/leaderboard")
  @ApiOperation("获取数独游戏排行榜")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "rankingName", value = "排行类型名", dataTypeClass = RankingType.class, required = true),
      @ApiImplicitParam(name = "sudokuLevelName", value = "数独等级名称", dataTypeClass = String.class, required = true),
      @ApiImplicitParam(name = "page", value = "当前查询页", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", dataTypeClass = Integer.class, required = true)
  })
  public Page<RankItemDataBO> getLeaderboard(@RequestParam RankingType rankingName, @RequestParam String sudokuLevelName,
      @RequestParam Integer page, @RequestParam @Range(min = 1, max = 20, message = "每页显示的条数在1-20个之间") Integer pageSize) {
    return gameRankService.getRanking(rankingName, sudokuLevelName, page, pageSize);
  }

  @GetMapping("/rankingName")
  @ApiOperation("获取排行类型名")
  public List<String> getRankingName() {
    return RankingType.getAllRankingTypeName();
  }

  @GetMapping("/rank")
  @ApiOperation("获取当前用户指定排行的排名")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "rankingName", value = "排行类型名", dataTypeClass = RankingType.class, required = true),
      @ApiImplicitParam(name = "sudokuLevelName", value = "数独等级名称", dataTypeClass = String.class, required = true),
  })
  public Long getCurrentUserRank(@RequestParam RankingType rankingName, @RequestParam String sudokuLevelName) {
    return gameRankService.getCurrentUserRank(rankingName, sudokuLevelName);
  }
}
