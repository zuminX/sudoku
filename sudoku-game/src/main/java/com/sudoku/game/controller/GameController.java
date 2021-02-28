package com.sudoku.game.controller;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.game.convert.UserAnswerInformationConvert;
import com.sudoku.game.model.bo.SudokuDataBO;
import com.sudoku.game.model.bo.SudokuGridInformationBO;
import com.sudoku.game.model.bo.UserAnswerInformationBO;
import com.sudoku.game.model.entity.SudokuLevel;
import com.sudoku.game.model.vo.UserAnswerInformationVO;
import com.sudoku.game.service.NormalGameRecordService;
import com.sudoku.game.service.SudokuRecordService;
import com.sudoku.game.service.SudokuService;
import com.sudoku.game.utils.sudoku.GameUtils;
import com.sudoku.game.validator.IsSudokuMatrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
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
@Api(tags = "游戏API接口")
public class GameController extends GameBaseController {

  private final SudokuService sudokuService;

  private final NormalGameRecordService normalGameRecordService;

  private final SudokuRecordService sudokuRecordService;

  private final UserAnswerInformationConvert userAnswerInformationConvert;

  private final GameUtils gameUtils;

  public GameController(SudokuService sudokuService, NormalGameRecordService normalGameRecordService,
      SudokuRecordService sudokuRecordService, UserAnswerInformationConvert userAnswerInformationConvert, GameUtils gameUtils) {
    this.sudokuService = sudokuService;
    this.normalGameRecordService = normalGameRecordService;
    this.sudokuRecordService = sudokuRecordService;
    this.userAnswerInformationConvert = userAnswerInformationConvert;
    this.gameUtils = gameUtils;
  }

  @GetMapping("/generateTopic")
  @ApiOperation("生成数独题目")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "level", value = "难度级别", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "isRecord", value = "是否记录", dataTypeClass = Boolean.class, required = true)})
  public SudokuDataBO generateSudokuTopic(@RequestParam SudokuLevel level,
      @RequestParam @NotNull(message = "是否记录游戏不能为空") Boolean isRecord) {
    SudokuDataBO sudokuDataBO = sudokuService.generateSudokuTopic(level, isRecord);
    insertGameRecord();
    return sudokuDataBO;
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
    updateGameRecord(userMatrix, userAnswerInformation.getSituation());
    gameUtils.removeSudokuRecord();
    return userAnswerInformationConvert.convert(userAnswerInformation);
  }

  /**
   * 新增当前的游戏记录
   */
  @Transactional
  protected void insertGameRecord() {
    if (gameUtils.isRecord()) {
      sudokuRecordService.insertNowGameRecord();
      normalGameRecordService.insertNowGameRecord();
    }
  }

  /**
   * 更新当前的游戏记录
   *
   * @param inputMatrix     用户输入的矩阵
   * @param answerSituation 回答情况
   */
  @Transactional
  protected void updateGameRecord(List<List<Integer>> inputMatrix, AnswerSituation answerSituation) {
    if (gameUtils.isRecord()) {
      sudokuRecordService.updateNowGameRecordEndTime();
      normalGameRecordService.updateGameRecord(inputMatrix, answerSituation);
    }
  }
}
