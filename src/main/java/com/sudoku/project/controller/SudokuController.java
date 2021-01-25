package com.sudoku.project.controller;

import com.sudoku.common.utils.sudoku.SudokuBuilder;
import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SudokuLevelVO;
import com.sudoku.project.service.SudokuLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sudoku")
@Validated
@Api(tags = "数独API接口")
public class SudokuController {

  private final SudokuLevelService sudokuLevelService;

  public SudokuController(SudokuLevelService sudokuLevelService) {
    this.sudokuLevelService = sudokuLevelService;
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
}
