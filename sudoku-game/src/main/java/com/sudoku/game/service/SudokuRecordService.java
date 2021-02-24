package com.sudoku.game.service;

import com.sudoku.game.convert.SudokuRecordConvert;
import com.sudoku.game.mapper.SudokuRecordMapper;
import com.sudoku.game.model.bo.SudokuRecordBO;
import com.sudoku.game.model.entity.SudokuRecord;
import com.sudoku.game.utils.sudoku.GameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数独记录业务层类
 */
@Service
public class SudokuRecordService {

  private final SudokuRecordMapper sudokuRecordMapper;

  private final SudokuRecordConvert sudokuRecordConvert;

  private final GameUtils gameUtils;

  public SudokuRecordService(SudokuRecordMapper sudokuRecordMapper, SudokuRecordConvert sudokuRecordConvert, GameUtils gameUtils) {
    this.sudokuRecordMapper = sudokuRecordMapper;
    this.sudokuRecordConvert = sudokuRecordConvert;
    this.gameUtils = gameUtils;
  }

  /**
   * 插入当前游戏的数独记录
   *
   */
  @Transactional
  public void insertNowGameRecord() {
    SudokuRecord sudokuRecord = sudokuRecordConvert.convert(gameUtils.getSudokuRecord());
    sudokuRecordMapper.insert(sudokuRecord);
    updateIdForSudokuRecord(sudokuRecord.getId());
  }

  /**
   * 更新当前游戏的数独记录的结束时间
   */
  @Transactional
  public void updateNowGameRecordEndTime() {
    SudokuRecordBO sudokuRecord = gameUtils.getSudokuRecord();
    sudokuRecordMapper.updateEndTimeById(sudokuRecord.getEndTime(), sudokuRecord.getId());
  }

  /**
   * 更新Redis中的数独记录的ID字段
   *
   * @param sudokuRecordId 数独记录ID
   */
  private void updateIdForSudokuRecord(Integer sudokuRecordId) {
    SudokuRecordBO sudokuRecordBO = gameUtils.getSudokuRecord();
    sudokuRecordBO.setId(sudokuRecordId);
    gameUtils.setSudokuRecord(sudokuRecordBO);
  }
}
