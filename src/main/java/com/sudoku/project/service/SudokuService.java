package com.sudoku.project.service;

import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.bo.SudokuGridInformationBO;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.model.vo.SubmitSudokuInformationVO;
import java.util.List;

/**
 * 数独业务层接口
 */
public interface SudokuService {

  /**
   * 生成数独题目
   *
   * @param sudokuLevel 数独等级
   * @param isRecord    是否记录
   * @return 数独题目
   */
  SudokuDataBO generateSudokuTopic(SudokuLevel sudokuLevel, Boolean isRecord);

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  SudokuGridInformationBO getHelp(List<List<Integer>> userMatrix);

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  SubmitSudokuInformationVO checkSudokuData(List<List<Integer>> userMatrix);

  /**
   * 是否记录游戏信息
   *
   * @return 记录返回true，不记录返回false
   */
  Boolean isRecordGameInformation();
}
