package com.sudoku.common.utils.sudoku;

import static com.sudoku.common.constant.consist.RedisKeys.GAME_RECORD_PREFIX;
import static com.sudoku.common.constant.enums.AnswerSituation.CORRECT;
import static com.sudoku.common.constant.enums.AnswerSituation.ERROR;
import static com.sudoku.common.constant.enums.AnswerSituation.IDENTICAL;
import static com.sudoku.common.utils.sudoku.SudokuUtils.isNotHole;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.bo.SudokuDataBO;
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
   * 从redis中获取当前游戏记录
   *
   * @return 游戏记录
   */
  public GameRecordBO getGameRecord() {
    return redisUtils.getObject(getGameRecordKey());
  }

  /**
   * 保存游戏记录至redis中
   *
   * @param gameRecord 游戏记录
   */
  public void setGameRecord(GameRecordBO gameRecord) {
    redisUtils.setObject(getGameRecordKey(), gameRecord, GAME_MAX_TIME, TimeUnit.HOURS);
  }

  /**
   * 移除数独游戏记录
   */
  public void removeGameRecord() {
    redisUtils.deleteObject(getGameRecordKey());
  }

  /**
   * 判断答题状态
   *
   * @param userMatrix   用户的数独矩阵数据
   * @param sudokuDataBO 数独数据
   * @return 用户答题状态
   */
  public static AnswerSituation judgeAnswerSituation(List<List<Integer>> userMatrix, SudokuDataBO sudokuDataBO) {
    AnswerSituation situation = compareByGenerateAnswer(userMatrix, sudokuDataBO);
    return situation.equals(CORRECT) ? compareBySudokuRule(userMatrix) : situation;
  }

  /**
   * 根据生成的数独答案进行比较
   *
   * @param userMatrix   用户的数独矩阵数据
   * @param sudokuDataBO 数独数据
   * @return 用户答题状态
   */
  private static AnswerSituation compareByGenerateAnswer(List<List<Integer>> userMatrix, SudokuDataBO sudokuDataBO) {
    int[][] matrix = sudokuDataBO.getMatrix();
    boolean[][] holes = sudokuDataBO.getHoles();

    AnswerSituation situation = IDENTICAL;
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (isNotHole(holes, i, j)) {
          continue;
        }
        Integer userValue = userMatrix.get(i).get(j);
        if (userValue == null) {
          return ERROR;
        }
        if (userValue != matrix[i][j]) {
          situation = CORRECT;
        }
      }
    }
    return situation;
  }

  /**
   * 根据数独规则进行比较
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题状态
   */
  private static AnswerSituation compareBySudokuRule(List<List<Integer>> userMatrix) {
    return SudokuUtils.checkSudokuValidity(userMatrix) ? CORRECT : ERROR;
  }

  /**
   * 获取游戏记录的key值
   *
   * @return 游戏记录在redis中的key值
   */
  private String getGameRecordKey() {
    return GAME_RECORD_PREFIX + SecurityUtils.getCurrentUserId();
  }
}
