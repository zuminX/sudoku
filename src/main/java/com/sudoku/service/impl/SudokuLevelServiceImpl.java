package com.sudoku.service.impl;

import com.sudoku.constant.enums.StatusCode;
import com.sudoku.convert.SudokuLevelConvert;
import com.sudoku.exception.GameException;
import com.sudoku.mapper.SudokuLevelMapper;
import com.sudoku.model.po.SudokuLevel;
import com.sudoku.model.vo.SudokuLevelVO;
import com.sudoku.service.SudokuLevelService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源业务层实现类
 */
@Service
public class SudokuLevelServiceImpl implements SudokuLevelService {

  @Autowired
  private SudokuLevelMapper sudokuLevelMapper;

  /**
   * 获取数独游戏的所有难度
   *
   * @return 数独等级显示层对象集合
   */
  @Override
  public List<SudokuLevelVO> getSudokuLevels() {
    return sudokuLevelMapper.selectAll().stream().map(SudokuLevelConvert.INSTANCE::convert).collect(Collectors.toList());
  }

  /**
   * 根据数独难度等级获取数独等级对象
   *
   * @param level 数独难度等级
   * @return 数独等级对象
   */
  @Override
  public SudokuLevel getSudokuLevel(int level) {
    //根据难度级别查找对应的数独级别信息
    SudokuLevel sudokuLevel = sudokuLevelMapper.selectByLevel(level);
    if (sudokuLevel == null) {
      throw new GameException(StatusCode.GAME_NOT_LEVEL);
    }
    return sudokuLevel;
  }
}
