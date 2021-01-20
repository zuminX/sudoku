package com.sudoku.project.controller;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.utils.sudoku.GameUtils;
import com.sudoku.common.utils.sudoku.SudokuBuilder;
import com.sudoku.common.validator.IsSudokuMatrix;
import com.sudoku.project.convert.UserAnswerInformationConvert;
import com.sudoku.project.model.bo.SudokuRecordBO;
import com.sudoku.project.model.bo.UserAnswerInformationBO;
import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.bo.SudokuGridInformationBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SudokuLevelVO;
import com.sudoku.project.model.vo.UserAnswerInformationVO;
import com.sudoku.project.service.NormalGameRecordService;
import com.sudoku.project.service.SudokuLevelService;
import com.sudoku.project.service.SudokuService;
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

  private final SudokuLevelService sudokuLevelService;

  private final NormalGameRecordService normalGameRecordService;

  private final UserAnswerInformationConvert userAnswerInformationConvert;

  private final GameUtils gameUtils;

  public GameController(SudokuService sudokuService, SudokuLevelService sudokuLevelService,
      NormalGameRecordService normalGameRecordService, UserAnswerInformationConvert userAnswerInformationConvert,
      GameUtils gameUtils) {
    this.sudokuService = sudokuService;
    this.normalGameRecordService = normalGameRecordService;
    this.userAnswerInformationConvert = userAnswerInformationConvert;
    this.sudokuLevelService = sudokuLevelService;
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
    cleanSudokuRecord();
    return sudokuService.generateSudokuTopic(level, isRecord);
  }

  @PostMapping("/help")
  @ApiOperation("获取当前数独游戏的提示信息")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = List.class, required = true)
  public SudokuGridInformationBO getHelp(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    return sudokuService.getHelp(userMatrix);
  }

  @PostMapping("/check")
  @ApiOperation("检查用户的数独数据")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = List.class, required = true)
  public UserAnswerInformationVO checkSudokuData(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    UserAnswerInformationBO userAnswerInformation = sudokuService.checkSudokuData(userMatrix);
    submitGameRecord(userMatrix, userAnswerInformation.getSituation());
    return userAnswerInformationConvert.convert(userAnswerInformation);
  }

  /**
   * 提交数独游戏记录
   */
  private void submitGameRecord(List<List<Integer>> userMatrix, AnswerSituation answerSituation) {
    SudokuRecordBO sudokuRecord = gameUtils.getSudokuRecord();
    if (sudokuRecord.isRecord()) {
      normalGameRecordService.insertGameRecord(userMatrix, answerSituation, sudokuRecord);
    }
    gameUtils.removeSudokuRecord();
  }

  /**
   * 清理数独游戏记录
   */
  private void cleanSudokuRecord() {
    if (gameUtils.getSudokuRecord() != null) {
      submitGameRecord(null, AnswerSituation.UNFINISHED);
    }
  }
}
