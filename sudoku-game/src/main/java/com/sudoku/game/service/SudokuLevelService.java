package com.sudoku.game.service;

import com.sudoku.game.convert.SudokuLevelConvert;
import com.sudoku.game.mapper.SudokuLevelMapper;
import com.sudoku.game.model.entity.SudokuLevel;
import com.sudoku.game.model.vo.SudokuLevelVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 数独等级业务层类
 */
@Service
public class SudokuLevelService {

  private final SudokuLevelMapper sudokuLevelMapper;

  private final SudokuLevelConvert sudokuLevelConvert;

  public SudokuLevelService(SudokuLevelMapper sudokuLevelMapper, SudokuLevelConvert sudokuLevelConvert) {
    this.sudokuLevelMapper = sudokuLevelMapper;
    this.sudokuLevelConvert = sudokuLevelConvert;
  }

  /**
   * 获取数独游戏的所有难度
   *
   * @return 数独等级显示层对象集合
   */
  public List<SudokuLevelVO> getSudokuLevels() {
    return sudokuLevelMapper.selectAll().stream().map(sudokuLevelConvert::convert).collect(Collectors.toList());
  }

  /**
   * 根据数独难度等级获取数独等级对象
   *
   * @param level 数独难度等级
   * @return 数独等级对象
   */
  public SudokuLevel getSudokuLevel(int level) {
    return sudokuLevelMapper.selectByLevel(level);
  }
}
