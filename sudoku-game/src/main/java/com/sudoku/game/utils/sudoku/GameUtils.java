package com.sudoku.game.utils.sudoku;

import static com.sudoku.common.constant.consist.RedisKeys.SUDOKU_RECORD_PREFIX;
import static com.sudoku.common.constant.enums.AnswerSituation.ERROR;
import static com.sudoku.common.constant.enums.AnswerSituation.IDENTICAL;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.game.model.bo.SudokuDataBO;
import com.sudoku.game.model.bo.SudokuRecordBO;
import com.sudoku.system.utils.SecurityUtils;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

/**
 * 游戏工具类
 */
@Component
public class GameUtils {

  /**
   * 每局数独游戏最长存在时间(h)
   */
  private static final int GAME_MAX_TIME = 2;

  private final RedisUtils redisUtils;

  public GameUtils(RedisUtils redisUtils) {
    this.redisUtils = redisUtils;
  }

  /**
   * 判断答题状态
   *
   * @param userMatrix   用户的数独矩阵数据
   * @param sudokuDataBO 数独数据
   * @return 用户答题状态
   */
  public static AnswerSituation judgeAnswerSituation(List<List<Integer>> userMatrix, SudokuDataBO sudokuDataBO) {
    int[][] matrix = sudokuDataBO.getMatrix();
    boolean[][] holes = sudokuDataBO.getHoles();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (SudokuUtils.isNotHole(holes, i, j)) {
          continue;
        }
        Integer userValue = userMatrix.get(i).get(j);
        if (userValue == null || userValue != matrix[i][j]) {
          return ERROR;
        }
      }
    }
    return IDENTICAL;
  }

  /**
   * 从redis中获取当前数独记录
   *
   * @return 数独记录
   */
  public SudokuRecordBO getSudokuRecord() {
    return redisUtils.getObject(getSudokuRecordKey());
  }

  /**
   * 保存数独记录至redis中
   *
   * @param sudokuRecord 数独记录
   */
  public void setSudokuRecord(SudokuRecordBO sudokuRecord) {
    redisUtils.setObject(getSudokuRecordKey(), sudokuRecord, GAME_MAX_TIME, TimeUnit.HOURS);
  }

  /**
   * 移除数独记录
   */
  public void removeSudokuRecord() {
    redisUtils.deleteObject(getSudokuRecordKey());
  }

  /**
   * 判断当前数独记录是否为记录模式
   *
   * @return 若是记录模式返回true，若不是记录模式或当前不存在记录则返回false
   */
  public boolean isRecord() {
    SudokuRecordBO sudokuRecord = getSudokuRecord();
    return sudokuRecord != null && sudokuRecord.isRecord();
  }

  /**
   * 获取数独记录的key值
   *
   * @return 数独记录在redis中的key值
   */
  private String getSudokuRecordKey() {
    return SUDOKU_RECORD_PREFIX + SecurityUtils.getCurrentUserId();
  }
}
