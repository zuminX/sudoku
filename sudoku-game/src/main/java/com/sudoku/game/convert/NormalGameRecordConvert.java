package com.sudoku.game.convert;

import com.sudoku.game.model.result.NormalGameRecordResultForHistory;
import com.sudoku.game.model.vo.NormalGameRecordVO;
import com.sudoku.game.utils.sudoku.SudokuUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 普通游戏记录对象转换器
 */
@Mapper(imports = SudokuUtils.class, uses = SudokuRecordConvert.class)
public interface NormalGameRecordConvert {

  /**
   * 将查询历史普通游戏记录的结果对象转换为普通游戏记录显示类对象
   *
   * @param normalGameRecordResult 查询历史普通游戏记录的结果对象
   * @return 普通游戏记录显示类对象
   */
  @Mapping(target = "inputMatrix", expression = "java(SudokuUtils.unzipToMatrix(normalGameRecordResult.getInputMatrix()))")
  @Mapping(target = "sudokuRecord", source = "sudokuRecordResult")
  NormalGameRecordVO convert(NormalGameRecordResultForHistory normalGameRecordResult);
}
