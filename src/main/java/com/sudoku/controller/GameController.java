package com.sudoku.controller;

import com.sudoku.model.dto.SudokuDataDTO;
import com.sudoku.model.dto.SudokuGridInformationDTO;
import com.sudoku.model.po.SudokuLevel;
import com.sudoku.model.vo.RankDataVO;
import com.sudoku.model.vo.SubmitSudokuInformationVO;
import com.sudoku.model.vo.SudokuLevelVO;
import com.sudoku.service.GameRecordService;
import com.sudoku.service.SudokuLevelService;
import com.sudoku.service.SudokuService;
import com.sudoku.service.UserGameInformationService;
import com.sudoku.validator.IsSudokuMatrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数独游戏的控制层
 */
@RestController
@RequestMapping("/game")
@Validated
@Api(tags = "数独游戏API接口")
public class GameController {

  @Autowired
  private SudokuService sudokuService;
  @Autowired
  private GameRecordService gameRecordService;
  @Autowired
  private UserGameInformationService userGameInformationService;
  @Autowired
  private SudokuLevelService sudokuLevelService;

  @GetMapping("/sudokuLevels")
  @ApiOperation("获取数独的所有难度等级")
  public List<SudokuLevelVO> getSudokuLevels() {
    return sudokuLevelService.getSudokuLevels();
  }

  @GetMapping("/generateTopic")
  @ApiOperation("生成数独题目")
  @ApiImplicitParams({@ApiImplicitParam(name = "level", value = "难度级别", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "isRecord", value = "是否记录", dataTypeClass = Boolean.class, required = true)})
  public SudokuDataDTO generateSudokuTopic(@RequestParam("level") Integer level,
      @RequestParam("isRecord") @NotNull(message = "是否记录游戏不能为空") Boolean isRecord) {
    SudokuLevel sudokuLevel = sudokuLevelService.getSudokuLevel(level);
    SudokuDataDTO sudokuDataDTO = sudokuService.generateSudokuTopic(sudokuLevel, isRecord);
    saveGameInformation();
    return sudokuDataDTO;
  }

  @PostMapping("/help")
  @ApiOperation("获取当前数独游戏的提示信息")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = ArrayList.class, required = true)
  public SudokuGridInformationDTO getHelp(
      @RequestBody @NotNull(message = "填写的数独矩阵数据不能为空") @IsSudokuMatrix ArrayList<ArrayList<Integer>> userMatrix) {
    return sudokuService.getHelp(userMatrix);
  }

  @PostMapping("/check")
  @ApiOperation("检查用户的数独数据")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = ArrayList.class, required = true)
  public SubmitSudokuInformationVO checkSudokuData(
      @RequestBody @NotNull(message = "填写的数独矩阵数据不能为空") @IsSudokuMatrix ArrayList<ArrayList<Integer>> userMatrix) {
    SubmitSudokuInformationVO submitSudokuInformationVO = sudokuService.checkSudokuData(userMatrix);
    saveGameInformation();
    return submitSudokuInformationVO;
  }

  @GetMapping("/rank")
  @ApiOperation("获取排行列表数据")
  public List<RankDataVO<?>> getRankList() {
    return userGameInformationService.getRankList();
  }

  /**
   * 保存游戏信息
   */
  private void saveGameInformation() {
    if (sudokuService.isRecordGameInformation()) {
      gameRecordService.saveGameRecord();
      userGameInformationService.updateUserGameInformation();
    }
  }

}
