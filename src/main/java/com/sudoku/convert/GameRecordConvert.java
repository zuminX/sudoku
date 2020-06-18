package com.sudoku.convert;

import com.sudoku.model.bo.GameRecordBO;
import com.sudoku.model.entity.GameRecord;
import com.sudoku.utils.PublicUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 游戏记录转换器
 */
@Mapper(imports = PublicUtils.class)
public interface GameRecordConvert {

  GameRecordConvert INSTANCE = Mappers.getMapper(GameRecordConvert.class);

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
