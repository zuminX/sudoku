package com.sudoku.service.impl;

import static com.sudoku.constant.consist.SessionKey.GAME_RECORD_KEY;
import static com.sudoku.constant.consist.SessionKey.IS_RECORD_KEY;
import static com.sudoku.utils.CoreUtils.setSessionAttribute;
import static com.sudoku.utils.PublicUtils.getRandomInt;

import com.sudoku.constant.enums.AnswerSituation;
import com.sudoku.convert.SubmitSudokuInformationConvert;
import com.sudoku.model.dto.GameRecordDTO;
import com.sudoku.model.dto.SubmitSudokuInformationDTO;
import com.sudoku.model.dto.SudokuDataDTO;
import com.sudoku.model.dto.SudokuGridInformationDTO;
import com.sudoku.model.po.SudokuLevel;
import com.sudoku.model.vo.SubmitSudokuInformationVO;
import com.sudoku.service.SudokuService;
import com.sudoku.utils.CoreUtils;
import com.sudoku.utils.PublicUtils;
import com.sudoku.utils.SudokuBuilder;
import com.sudoku.utils.SudokuUtils;
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
  public SudokuDataDTO generateSudokuTopic(SudokuLevel sudokuLevel, Boolean isRecord) {
    //生成数独终盘
    SudokuDataDTO sudokuDataDTO = SudokuBuilder.generateSudokuFinal(sudokuLevel.getMinEmpty(), sudokuLevel.getMinEmpty());
    saveGameRecord(sudokuDataDTO, sudokuLevel.getId(), isRecord);
    //将数独终盘转换为数独题目并返回
    return SudokuUtils.generateSudokuTopic(sudokuDataDTO);
  }

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  @Override
  public SudokuGridInformationDTO getHelp(ArrayList<ArrayList<Integer>> userMatrix) {
    //获取session中的游戏记录
    GameRecordDTO gameRecord = (GameRecordDTO) session.getAttribute(GAME_RECORD_KEY);
    //寻找用户错误的数独格子数据
    ArrayList<SudokuGridInformationDTO> errorGridInformationList = findErrorGridInformation(gameRecord.getSudokuDataDTO(), userMatrix);
    int size = errorGridInformationList.size();
    //随机返回一个数据
    return size > 0 ? errorGridInformationList.get(getRandomInt(0, size - 1)) : null;
  }

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  @Override
  public SubmitSudokuInformationVO checkSudokuData(ArrayList<ArrayList<Integer>> userMatrix) {
    //获取session中的游戏记录
    GameRecordDTO gameRecord = (GameRecordDTO) session.getAttribute(GAME_RECORD_KEY);
    gameRecord.setEndTime(new Date());

    SudokuDataDTO sudokuDataDTO = gameRecord.getSudokuDataDTO();
    int[][] matrix = sudokuDataDTO.getMatrix();
    int[][] holes = sudokuDataDTO.getHoles();
    AnswerSituation situation = judgeAnswerSituation(userMatrix, sudokuDataDTO, matrix, holes);
    gameRecord.setCorrect(situation.isRight());

    //将游戏记录存放到session中
    setSessionAttribute(session, GAME_RECORD_KEY, gameRecord);

    SubmitSudokuInformationDTO informationDTO = SubmitSudokuInformationDTO.builder().situation(situation).matrix(matrix)
        .spendTime(PublicUtils.getTwoDateAbsDiff(gameRecord.getEndTime(), gameRecord.getStartTime())).build();
    return SubmitSudokuInformationConvert.INSTANCE.convert(informationDTO);
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
   * 判断答题状态
   *
   * @param userMatrix    用户的数独矩阵数据
   * @param sudokuDataDTO 数独数据
   * @param matrix        数独矩阵
   * @param holes         题目空缺数组
   * @return 用户答题状态
   */
  private AnswerSituation judgeAnswerSituation(ArrayList<ArrayList<Integer>> userMatrix, SudokuDataDTO sudokuDataDTO, int[][] matrix,
      int[][] holes) {
    //初始为与答案一致
    AnswerSituation situation = AnswerSituation.IDENTICAL;
    OUT:
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        Integer userValue = userMatrix.get(i).get(j);
        if (userValue != null) {
          //用户数据与答案不一致
          if (userValue != matrix[i][j]) {
            //若该处不是空缺部分，则说明数据被用户篡改，直接视为答案错误
            if (holes[i][j] == 0) {
              situation = AnswerSituation.ERROR;
              break OUT;
            }
            //设置为非标准答案
            situation = AnswerSituation.CORRECT;
          }
          //该格数据为空，答案错误
        } else {
          situation = AnswerSituation.ERROR;
          break OUT;
        }
      }
    }
    //判断用户的答案是否符合数独规则
    if (situation.equals(AnswerSituation.CORRECT)) {
      situation = SudokuUtils.checkSudokuValidity(sudokuDataDTO) ? AnswerSituation.CORRECT : AnswerSituation.ERROR;
    }
    return situation;
  }

  /**
   * 保存游戏记录
   *
   * @param sudokuDataDTO 数独数据
   * @param slid          数独难度ID
   * @param isRecord      是否记录
   */
  private void saveGameRecord(SudokuDataDTO sudokuDataDTO, Integer slid, Boolean isRecord) {
    GameRecordDTO gameRecordDTO = GameRecordDTO.builder().sudokuDataDTO(sudokuDataDTO).startTime(new Date()).correct(false)
        .uid(CoreUtils.getNowUser().getId()).slid(slid).build();
    //将游戏记录信息存放到session中
    setSessionAttribute(session, GAME_RECORD_KEY, gameRecordDTO);
    setSessionAttribute(session, IS_RECORD_KEY, isRecord);
  }

  /**
   * 寻找错误的数独格子信息
   *
   * @param sudokuDataDTO 数独数据
   * @param userMatrix    用户的数独矩阵数据
   * @return 用户错误的数独格子信息
   */
  private ArrayList<SudokuGridInformationDTO> findErrorGridInformation(SudokuDataDTO sudokuDataDTO,
      ArrayList<ArrayList<Integer>> userMatrix) {
    ArrayList<SudokuGridInformationDTO> errorGridInformationList = new ArrayList<>();
    int[][] matrix = sudokuDataDTO.getMatrix();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        Integer userValue = userMatrix.get(i).get(j);
        //如果该格子为空或其值与答案不同，将对应的行、列和正确的值加入到错误信息数组中
        if (userValue == null || userValue != matrix[i][j]) {
          errorGridInformationList.add(new SudokuGridInformationDTO(i, j, matrix[i][j]));
        }
      }
    }
    return errorGridInformationList;
  }

}
