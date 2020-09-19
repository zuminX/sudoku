package com.sudoku.project.controller;

import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.bo.SudokuGridInformationBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.RankDataVO;
import com.sudoku.project.model.vo.SubmitSudokuInformationVO;
import com.sudoku.project.model.vo.SudokuLevelVO;
import com.sudoku.project.service.GameRecordService;
import com.sudoku.project.service.SudokuLevelService;
import com.sudoku.project.service.SudokuService;
import com.sudoku.project.service.UserGameInformationService;
import com.sudoku.common.validator.IsSudokuMatrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
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
  @PreAuthorize("@ss.hasPermission('sudoku:level:list')")
  @ApiOperation("获取数独的所有难度等级")
  public List<SudokuLevelVO> getSudokuLevels() {
    return sudokuLevelService.getSudokuLevels();
  }

  @GetMapping("/generateTopic")
  @PreAuthorize("@ss.hasPermission('sudoku:game:generator')")
  @ApiOperation("生成数独题目")
  @ApiImplicitParams({@ApiImplicitParam(name = "level", value = "难度级别", dataTypeClass = Integer.class, required = true),
      @ApiImplicitParam(name = "isRecord", value = "是否记录", dataTypeClass = Boolean.class, required = true)})
  public SudokuDataBO generateSudokuTopic(@RequestParam Integer level, @RequestParam @NotNull(message = "是否记录游戏不能为空") Boolean isRecord) {
    SudokuLevel sudokuLevel = sudokuLevelService.getSudokuLevel(level);
    SudokuDataBO sudokuDataBO = sudokuService.generateSudokuTopic(sudokuLevel, isRecord);
    saveGameInformation();
    return sudokuDataBO;
  }

  @PostMapping("/help")
  @PreAuthorize("@ss.hasPermission('sudoku:game:help')")
  @ApiOperation("获取当前数独游戏的提示信息")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = ArrayList.class, required = true)
  public SudokuGridInformationBO getHelp(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    return sudokuService.getHelp(userMatrix);
  }

  @PostMapping("/check")
  @PreAuthorize("@ss.hasPermission('sudoku:game:check')")
  @ApiOperation("检查用户的数独数据")
  @ApiImplicitParam(name = "userMatrix", value = "用户的数独矩阵数据", dataTypeClass = ArrayList.class, required = true)
  public SubmitSudokuInformationVO checkSudokuData(@RequestBody @IsSudokuMatrix List<List<Integer>> userMatrix) {
    SubmitSudokuInformationVO submitSudokuInformationVO = sudokuService.checkSudokuData(userMatrix);
    saveGameInformation();
    return submitSudokuInformationVO;
  }

  @GetMapping("/rank")
  @PreAuthorize("@ss.hasPermission('sudoku:rank:list')")
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
