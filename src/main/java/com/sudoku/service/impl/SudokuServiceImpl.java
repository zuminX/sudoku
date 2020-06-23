package com.sudoku.service.impl;

import static com.sudoku.constant.consist.SessionKey.GAME_RECORD_KEY;
import static com.sudoku.constant.consist.SessionKey.IS_RECORD_KEY;
import static com.sudoku.constant.enums.AnswerSituation.CORRECT;
import static com.sudoku.constant.enums.AnswerSituation.ERROR;
import static com.sudoku.constant.enums.AnswerSituation.IDENTICAL;
import static com.sudoku.utils.CoreUtils.setSessionAttribute;
import static com.sudoku.utils.PublicUtils.getRandomInt;
import static com.sudoku.utils.sudoku.SudokuUtils.isNotHole;

import com.sudoku.constant.enums.AnswerSituation;
import com.sudoku.convert.SubmitSudokuInformationConvert;
import com.sudoku.model.bo.GameRecordBO;
import com.sudoku.model.bo.SubmitSudokuInformationBO;
import com.sudoku.model.bo.SudokuDataBO;
import com.sudoku.model.bo.SudokuGridInformationBO;
import com.sudoku.model.entity.SudokuLevel;
import com.sudoku.model.vo.SubmitSudokuInformationVO;
import com.sudoku.service.SudokuService;
import com.sudoku.utils.CoreUtils;
import com.sudoku.utils.PublicUtils;
import com.sudoku.utils.sudoku.SudokuBuilder;
import com.sudoku.utils.sudoku.SudokuUtils;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数独业务层实现类
 */
@Service
public class SudokuServiceImpl implements SudokuService {

  @Autowired
  private HttpSession session;

  /**
   * 生成数独题目
   *
   * @param sudokuLevel 数独等级
   * @param isRecord    是否记录
   * @return 数独题目
   */
  @Override
  public SudokuDataBO generateSudokuTopic(SudokuLevel sudokuLevel, Boolean isRecord) {
    SudokuDataBO sudokuDataBO = SudokuBuilder.generateSudokuFinal(sudokuLevel.getMinEmpty(), sudokuLevel.getMinEmpty());
    saveGameRecord(sudokuDataBO, sudokuLevel.getId(), isRecord);
    return SudokuUtils.generateSudokuTopic(sudokuDataBO);
  }

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  @Override
  public SudokuGridInformationBO getHelp(ArrayList<ArrayList<Integer>> userMatrix) {
    GameRecordBO gameRecord = (GameRecordBO) session.getAttribute(GAME_RECORD_KEY);
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
  public SubmitSudokuInformationVO checkSudokuData(ArrayList<ArrayList<Integer>> userMatrix) {
    GameRecordBO gameRecord = (GameRecordBO) session.getAttribute(GAME_RECORD_KEY);
    gameRecord.setEndTime(new Date());

    SudokuDataBO sudokuDataBO = gameRecord.getSudokuDataBO();
    AnswerSituation situation = judgeAnswerSituation(userMatrix, sudokuDataBO);
    gameRecord.setCorrect(situation.isRight());

    setSessionAttribute(session, GAME_RECORD_KEY, gameRecord);

    return getUserAnswerResult(gameRecord, situation);
  }


  /**
   * 是否记录游戏信息
   *
   * @return 记录返回true，不记录返回false
   */
  @Override
  public Boolean isRecordGameInformation() {
    return (Boolean) session.getAttribute(IS_RECORD_KEY);
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
    SubmitSudokuInformationBO informationDTO = SubmitSudokuInformationBO.builder()
        .situation(situation)
        .matrix(gameRecord.getSudokuDataBO().getMatrix())
        .spendTime(PublicUtils.getTwoDateAbsDiff(gameRecord.getEndTime(), gameRecord.getStartTime()))
        .build();
    return SubmitSudokuInformationConvert.INSTANCE.convert(informationDTO);
  }

  /**
   * 判断答题状态
   *
   * @param userMatrix   用户的数独矩阵数据
   * @param sudokuDataBO 数独数据
   * @return 用户答题状态
   */
  private AnswerSituation judgeAnswerSituation(ArrayList<ArrayList<Integer>> userMatrix, SudokuDataBO sudokuDataBO) {
    AnswerSituation situation = compareByGenerateAnswer(userMatrix, sudokuDataBO);
    if (situation.equals(CORRECT)) {
      return compareBySudokuRule(userMatrix);
    }
    return situation;
  }

  /**
   * 根据生成的数独答案进行比较
   *
   * @param userMatrix   用户的数独矩阵数据
   * @param sudokuDataBO 数独数据
   * @return 用户答题状态
   */
  private AnswerSituation compareByGenerateAnswer(ArrayList<ArrayList<Integer>> userMatrix, SudokuDataBO sudokuDataBO) {
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
  private AnswerSituation compareBySudokuRule(ArrayList<ArrayList<Integer>> userMatrix) {
    return SudokuUtils.checkSudokuValidity(PublicUtils.unwrap(userMatrix)) ? CORRECT : ERROR;
  }

  /**
   * 保存游戏记录
   *
   * @param sudokuDataBO 数独数据
   * @param slid         数独难度ID
   * @param isRecord     是否记录
   */
  private void saveGameRecord(SudokuDataBO sudokuDataBO, Integer slid, Boolean isRecord) {
    setSessionAttribute(session, GAME_RECORD_KEY, GameRecordBO.buildInit(sudokuDataBO, CoreUtils.getNowUserId(), slid));
    setSessionAttribute(session, IS_RECORD_KEY, isRecord);
  }

  /**
   * 寻找错误的数独格子信息
   *
   * @param sudokuDataBO 数独数据
   * @param userMatrix   用户的数独矩阵数据
   * @return 用户错误的数独格子信息
   */
  private ArrayList<SudokuGridInformationBO> findErrorGridInformation(SudokuDataBO sudokuDataBO,
      ArrayList<ArrayList<Integer>> userMatrix) {
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
