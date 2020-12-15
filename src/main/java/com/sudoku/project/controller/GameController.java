package com.sudoku.project.controller;

import com.sudoku.common.utils.sudoku.GameUtils;
import com.sudoku.common.utils.sudoku.SudokuBuilder;
import com.sudoku.common.validator.IsSudokuMatrix;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.bo.SudokuGridInformationBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.entity.UserGameInformation;
import com.sudoku.project.model.vo.SudokuLevelVO;
import com.sudoku.project.model.vo.UserAnswerInformationVO;
import com.sudoku.project.service.GameRankService;
import com.sudoku.project.service.GameRecordService;
import com.sudoku.project.service.SudokuLevelService;
import com.sudoku.project.service.SudokuService;
import com.sudoku.project.service.UserGameInformationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@Validated
@Api(tags = "数独游戏API接口")
public class GameController extends BaseController {

  private final SudokuService sudokuService;

  private final GameRecordService gameRecordService;

  private final UserGameInformationService userGameInformationService;

  private final SudokuLevelService sudokuLevelService;

  private final GameRankService gameRankService;

  private final GameUtils gameUtils;

  public GameController(SudokuService sudokuService, GameRecordService gameRecordService,
      UserGameInformationService userGameInformationService, SudokuLevelService sudokuLevelService, GameRankService gameRankService,
      GameUtils gameUtils) {
    this.sudokuService = sudokuService;
    this.gameRecordService = gameRecordService;
    this.userGameInformationService = userGameInformationService;
    this.sudokuLevelService = sudokuLevelService;
    this.gameRankService = gameRankService;
    this.gameUtils = gameUtils;
  }

  @GetMapping("/sudokuLevels")
  @ApiOperation("获取数独的所有难度等级")
  public List<SudokuLevelVO> getSudokuLevels() {
    return sudokuLevelService.getSudokuLevels();
  }

  @GetMapping("/generateSudokuFinal")
  @PreAuthorize("@ss.hasPermission('sudoku:final:generate')")
  @ApiOperation("生成数独终盘")
  @ApiImplicitParam(name = "sudokuLevel", value = "难度级别", dataTypeClass = Integer.class, required = true)
  public SudokuDataBO generateSudokuFinal(@RequestParam SudokuLevel sudokuLevel) {
    return SudokuBuilder.generateSudokuFinal(sudokuLevel.getMinEmpty(), sudokuLevel.getMaxEmpty());
  }

  @GetMapping("/generateTopic")
  @ApiOperation("生成数独题目")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "level", value = "难度级别", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "isRecord", value = "是否记录", dataTypeClass = Boolean.class, required = true)})
  public SudokuDataBO generateSudokuTopic(@RequestParam SudokuLevel level,
      @RequestParam @NotNull(message = "是否记录游戏不能为空") Boolean isRecord) {
    cleanGameRecord();
    return sudokuService.generateSudokuTopic(level, isRecord);
  }

  @PostMapping("/help")
  @ApiOperation("获取当前数独游戏的提示信息")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = List.class, required = true)
  public SudokuGridInformationBO getHelp(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    return sudokuService.getHelp(userMatrix);
  }

  @GetMapping("/clean")
  @ApiOperation("清理数独游戏记录，确保状态的正确性")
  public void cleanGameRecord() {
    GameRecordBO gameRecordBO = gameUtils.getGameRecord();
    if (gameRecordBO != null) {
      gameRecordService.cleanRedisGameRecord(gameRecordBO);
    }
  }

  @PostMapping("/check")
  @ApiOperation("检查用户的数独数据")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = List.class, required = true)
  public UserAnswerInformationVO checkSudokuData(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    UserAnswerInformationVO userAnswerInformationVO = sudokuService.checkSudokuData(userMatrix);
    if (sudokuService.isRecordGameInformation()) {
      GameRecordBO gameRecordBO = gameUtils.getGameRecord();
      gameRecordService.insertGameRecord(gameRecordBO);
      UserGameInformation userGameInformation = userGameInformationService.updateUserGameInformation(gameRecordBO);
      gameRankService.updateCurrentUserRank(userGameInformation);
    }
    gameUtils.removeGameRecord();
    return userAnswerInformationVO;
  }
}
