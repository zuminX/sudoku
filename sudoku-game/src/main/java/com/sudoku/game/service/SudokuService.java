package com.sudoku.game.service;

import static com.sudoku.common.utils.PublicUtils.getRandomInt;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.TwoDimensionalListUtils;
import com.sudoku.game.model.bo.SudokuDataBO;
import com.sudoku.game.model.bo.SudokuGridInformationBO;
import com.sudoku.game.model.bo.SudokuRecordBO;
import com.sudoku.game.model.bo.UserAnswerInformationBO;
import com.sudoku.game.model.entity.SudokuLevel;
import com.sudoku.game.utils.sudoku.GameUtils;
import com.sudoku.game.utils.sudoku.SudokuBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 数独业务层类
 */
@Service
public class SudokuService {

  private final GameUtils gameUtils;

  public SudokuService(GameUtils gameUtils) {
    this.gameUtils = gameUtils;
  }

  /**
   * 生成数独题目
   *
   * @param sudokuLevel 数独等级
   * @param isRecord    是否记录
   * @return 数独题目
   */
  public SudokuDataBO generateSudokuTopic(SudokuLevel sudokuLevel, Boolean isRecord) {
    SudokuDataBO sudokuDataBO = SudokuBuilder.generateSudokuFinal(sudokuLevel.getMinEmpty(), sudokuLevel.getMaxEmpty());
    saveGameRecord(sudokuDataBO, sudokuLevel.getId(), isRecord);
    return sudokuDataBO.hideVacancyGrid();
  }

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  public SudokuGridInformationBO getHelp(List<List<Integer>> userMatrix) {
    SudokuRecordBO sudokuRecord = gameUtils.getSudokuRecord();
    ArrayList<SudokuGridInformationBO> errorGridInformationList = findErrorGridInformation(sudokuRecord.getSudokuDataBO(), userMatrix);
    return randomGridInformation(errorGridInformationList);
  }

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  public UserAnswerInformationBO checkSudokuData(List<List<Integer>> userMatrix) {
    updateEndTimeForSudokuRecord();

    SudokuRecordBO sudokuRecord = gameUtils.getSudokuRecord();
    SudokuDataBO sudokuDataBO = gameUtils.getSudokuRecord().getSudokuDataBO();

    AnswerSituation situation = GameUtils.judgeAnswerSituation(userMatrix, sudokuDataBO);

    return UserAnswerInformationBO.builder()
        .situation(situation)
        .matrix(sudokuDataBO.getMatrix())
        .spendTime(PublicUtils.computeAbsDiff(sudokuRecord.getEndTime(), sudokuRecord.getStartTime()))
        .build();
  }

  /**
   * 更新Redis中的数独记录的结束时间字段
   */
  private void updateEndTimeForSudokuRecord() {
    SudokuRecordBO sudokuRecord = gameUtils.getSudokuRecord();
    sudokuRecord.setEndTime(LocalDateTime.now());
    gameUtils.setSudokuRecord(sudokuRecord);
  }

  /**
   * 随机返回一个格子信息
   *
   * @param errorGridInformationList 错误的数独格子信息列表
   * @return 数独格子信息
   */
  private SudokuGridInformationBO randomGridInformation(ArrayList<SudokuGridInformationBO> errorGridInformationList) {
    int size = errorGridInformationList.size();
    return size > 0 ? errorGridInformationList.get(getRandomInt(0, size - 1)) : null;
  }

  /**
   * 保存游戏记录
   *
   * @param sudokuDataBO  数独数据
   * @param sudokuLevelId 数独难度ID
   * @param isRecord      是否记录
   */
  private void saveGameRecord(SudokuDataBO sudokuDataBO, Integer sudokuLevelId, Boolean isRecord) {
    gameUtils.setSudokuRecord(SudokuRecordBO.builder()
        .sudokuDataBO(sudokuDataBO)
        .startTime(LocalDateTime.now())
        .sudokuLevelId(sudokuLevelId)
        .isRecord(isRecord)
        .build());
  }

  /**
   * 寻找错误的数独格子信息
   *
   * @param sudokuDataBO 数独数据
   * @param userMatrix   用户的数独矩阵数据
   * @return 用户错误的数独格子信息
   */
  private ArrayList<SudokuGridInformationBO> findErrorGridInformation(SudokuDataBO sudokuDataBO, List<List<Integer>> userMatrix) {
    ArrayList<SudokuGridInformationBO> errorGridInformationList = new ArrayList<>();
    TwoDimensionalListUtils.forEachIfNotEquals(sudokuDataBO.getMatrix(), userMatrix,
        (i, j, topicValue, userValue) -> errorGridInformationList.add(new SudokuGridInformationBO(i, j, topicValue)));
    return errorGridInformationList;
  }

}
