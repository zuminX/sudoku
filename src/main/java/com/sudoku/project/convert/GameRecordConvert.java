package com.sudoku.project.convert;

import com.sudoku.common.utils.PublicUtils;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.entity.GameRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 游戏记录对象转换器
 */
@Mapper(imports = PublicUtils.class)
public interface GameRecordConvert {

  /**
   * 将游戏记录传输层对象转换为对应表对象
   * <p>
   * 将数独数据中的数独矩阵和题目空缺数组转换为字符串
   *
   * @param gameRecord 提交数独信息传输层对象
   * @return 游戏记录表对应的对象
   */
  @Mapping(target = "sudokuMatrix", expression = "java(PublicUtils.compressionMatrix(gameRecord.getSudokuDataBO().getMatrix()))")
  @Mapping(target = "sudokuHoles", expression = "java(PublicUtils.compressionMatrix(gameRecord.getSudokuDataBO().getHoles()))")
  GameRecord convert(GameRecordBO gameRecord);
}
