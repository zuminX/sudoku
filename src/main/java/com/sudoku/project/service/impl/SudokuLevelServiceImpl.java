package com.sudoku.project.service.impl;

import com.sudoku.project.convert.SudokuLevelConvert;
import com.sudoku.project.mapper.SudokuLevelMapper;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SudokuLevelVO;
import com.sudoku.project.service.SudokuLevelService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 数独等级业务层实现类
 */
@Service
public class SudokuLevelServiceImpl implements SudokuLevelService {

  private final SudokuLevelMapper sudokuLevelMapper;
  private final SudokuLevelConvert sudokuLevelConvert;

  public SudokuLevelServiceImpl(SudokuLevelMapper sudokuLevelMapper,
      SudokuLevelConvert sudokuLevelConvert) {
    this.sudokuLevelMapper = sudokuLevelMapper;
    this.sudokuLevelConvert = sudokuLevelConvert;
  }

  /**
   * 获取数独游戏的所有难度
   *
   * @return 数独等级显示层对象集合
   */
  @Override
  public List<SudokuLevelVO> getSudokuLevels() {
    return sudokuLevelMapper.selectAll().stream().map(sudokuLevelConvert::convert).collect(Collectors.toList());
  }

  /**
   * 根据数独难度等级获取数独等级对象
   *
   * @param level 数独难度等级
   * @return 数独等级对象
   */
  @Override
  public SudokuLevel getSudokuLevel(int level) {
    return sudokuLevelMapper.selectByLevel(level);
  }
}
