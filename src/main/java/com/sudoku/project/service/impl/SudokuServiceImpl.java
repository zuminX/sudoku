package com.sudoku.project.service.impl;

import static com.sudoku.common.utils.PublicUtils.getRandomInt;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.utils.sudoku.GameUtils;
import com.sudoku.common.utils.PublicUtils;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.common.utils.sudoku.SudokuBuilder;
import com.sudoku.project.convert.SubmitSudokuInformationConvert;
import com.sudoku.project.model.bo.GameRecordBO;
import com.sudoku.project.model.bo.SubmitSudokuInformationBO;
import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.bo.SudokuGridInformationBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SubmitSudokuInformationVO;
import com.sudoku.project.service.SudokuService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 数独业务层实现类
 */
@Service
public class SudokuServiceImpl implements SudokuService {

  private final GameUtils gameUtils;
  private final SubmitSudokuInformationConvert submitSudokuInformationConvert;

  public SudokuServiceImpl(GameUtils gameUtils,
      SubmitSudokuInformationConvert submitSudokuInformationConvert) {
    this.gameUtils = gameUtils;
    this.submitSudokuInformationConvert = submitSudokuInformationConvert;
  }

  /**
   * 生成数独题目
   *
   * @param sudokuLevel 数独等级
   * @param isRecord    是否记录
   * @return 数独题目
   */
  @Override
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
  @Override
  public SudokuGridInformationBO getHelp(List<List<Integer>> userMatrix) {
    GameRecordBO gameRecord = gameUtils.getGameRecord();
    ArrayList<SudokuGridInformationBO> errorGridInformationList = findErrorGridInformation(gameRecord.getSudokuDataBO(), userMatrix);
    return randomGridInformation(errorGridInformationList);
  }

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  @Override
  public SubmitSudokuInformationVO checkSudokuData(List<List<Integer>> userMatrix) {
    GameRecordBO gameRecord = gameUtils.getGameRecord();
    gameRecord.setEndTime(LocalDateTime.now());

    SudokuDataBO sudokuDataBO = gameRecord.getSudokuDataBO();
    AnswerSituation situation = gameUtils.judgeAnswerSituation(userMatrix, sudokuDataBO);
    gameRecord.setCorrect(situation.isRight());

    gameUtils.setGameRecord(gameRecord);

    return getUserAnswerResult(gameRecord, situation);
  }

  /**
   * 是否记录游戏信息
   *
   * @return 记录返回true，不记录返回false
   */
  @Override
  public Boolean isRecordGameInformation() {
    return gameUtils.getGameRecord().isRecord();
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
   * 获取用户的答题结果
   *
   * @param gameRecord 游戏记录
   * @param situation  答题情况
   * @return 用户答题情况
   */
  private SubmitSudokuInformationVO getUserAnswerResult(GameRecordBO gameRecord, AnswerSituation situation) {
    SubmitSudokuInformationBO informationBO = SubmitSudokuInformationBO.builder()
        .situation(situation)
        .matrix(gameRecord.getSudokuDataBO().getMatrix())
        .spendTime(PublicUtils.computeAbsDiff(gameRecord.getEndTime(), gameRecord.getStartTime()))
        .build();
    return submitSudokuInformationConvert.convert(informationBO);
  }

  /**
   * 保存游戏记录
   *
   * @param sudokuDataBO  数独数据
   * @param sudokuLevelId 数独难度ID
   * @param isRecord      是否记录
   */
  private void saveGameRecord(SudokuDataBO sudokuDataBO, Integer sudokuLevelId, Boolean isRecord) {
    gameUtils.setGameRecord(GameRecordBO.builder()
        .startTime(LocalDateTime.now())
        .sudokuDataBO(sudokuDataBO)
        .sudokuLevelId(sudokuLevelId)
        .userId(SecurityUtils.getCurrentUserId())
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
    int[][] matrix = sudokuDataBO.getMatrix();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        Integer userValue = userMatrix.get(i).get(j);
        if (userValue == null || userValue != matrix[i][j]) {
          errorGridInformationList.add(new SudokuGridInformationBO(i, j, matrix[i][j]));
        }
      }
    }
    return errorGridInformationList;
  }

}
