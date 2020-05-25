package com.sudoku.service;

import com.sudoku.model.po.SudokuLevel;
import com.sudoku.model.vo.SudokuLevelVO;
import java.util.List;

/**
 * 资源业务层接口
 */
public interface SudokuLevelService {

  /**
   * 获取数独游戏的所有难度
   *
   * @return 数独等级显示层对象集合
   */
  List<SudokuLevelVO> getSudokuLevels();

  /**
   * 根据数独难度等级获取数独等级对象
   *
   * @param level 数独难度等级
   * @return 数独等级对象
   */
  SudokuLevel getSudokuLevel(int level);
}
