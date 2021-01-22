package com.sudoku.project.convert;

import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.sudoku.SudokuUtils;
import com.sudoku.project.model.bo.SudokuRecordBO;
import com.sudoku.project.model.body.RaceInformationBody;
import com.sudoku.project.model.entity.SudokuRecord;
import com.sudoku.project.model.result.SudokuRecordResultForHistory;
import com.sudoku.project.model.vo.SudokuRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 数独记录对象转换器
 */
@Mapper(imports = {PublicUtils.class, SudokuUtils.class})
public interface SudokuRecordConvert {

  /**
   * 将数独记录业务层对象转换为对应实体类对象
   * <p>
   * 将数独数据中的数独矩阵和题目空缺数组转换为字符串
   *
   * @param sudokuRecord 数独记录业务层对象
   * @return 数独记录表对应的实体类对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "sudokuMatrix", expression = "java(PublicUtils.compressionIntArray(sudokuRecord.getSudokuDataBO().getMatrix()))")
  @Mapping(target = "sudokuHoles", expression = "java(PublicUtils.compressionBoolArray(sudokuRecord.getSudokuDataBO().getHoles()))")
  SudokuRecord convert(SudokuRecordBO sudokuRecord);

  /**
   * 将竞赛信息对象转换为数独记录对象
   *
   * @param raceInformation 竞赛信息对象
   * @return 数独记录对象
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "sudokuMatrix", expression = "java(PublicUtils.compressionIntList(raceInformation.getMatrix()))")
  @Mapping(target = "sudokuHoles", expression = "java(PublicUtils.compressionBoolList(raceInformation.getHoles()))")
  @Mapping(target = "startTime", source = "raceInformation.raceTimeRange.start")
  @Mapping(target = "endTime", source = "raceInformation.raceTimeRange.end")
  SudokuRecord convert(RaceInformationBody raceInformation);

  /**
   * 将查询历史数独记录的结果对象转换为数独记录显示对象
   *
   * @param sudokuRecordResult 查询历史数独记录的结果对象
   * @return 数独记录显示对象
   */
  @Mapping(target = "sudokuMatrix", expression = "java(SudokuUtils.unzipToMatrix(sudokuRecordResult.getSudokuMatrix()))")
  @Mapping(target = "sudokuHoles", expression = "java(SudokuUtils.unzipToHoles(sudokuRecordResult.getSudokuHoles()))")
  SudokuRecordVO convert(SudokuRecordResultForHistory sudokuRecordResult);

}
