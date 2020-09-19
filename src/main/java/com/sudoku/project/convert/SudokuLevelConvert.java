package com.sudoku.project.convert;

import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SudokuLevelVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数独等级转换器
 */
@Mapper
public interface SudokuLevelConvert {

  SudokuLevelConvert INSTANCE = Mappers.getMapper(SudokuLevelConvert.class);

  /**
   * 将数独等级传输层对象转换为显示层对象
   *
   * @param sudokuLevel 数独等级的传输层对象
   * @return 数独等级的显示层对象
   */
  SudokuLevelVO convert(SudokuLevel sudokuLevel);
}
