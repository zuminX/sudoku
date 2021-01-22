package com.sudoku.project.convert;

import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SudokuLevelVO;
import org.mapstruct.Mapper;

/**
 * 数独等级镀锡转换器
 */
@Mapper
public interface SudokuLevelConvert {

  /**
   * 将数独等级对象转换为显示对象
   *
   * @param sudokuLevel 数独等级对象
   * @return 数独等级显示对象
   */
  SudokuLevelVO convert(SudokuLevel sudokuLevel);
}
