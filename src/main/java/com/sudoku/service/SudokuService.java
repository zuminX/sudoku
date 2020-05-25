package com.sudoku.service;

import com.sudoku.model.dto.SudokuDataDTO;
import com.sudoku.model.dto.SudokuGridInformationDTO;
import com.sudoku.model.po.SudokuLevel;
import com.sudoku.model.vo.SubmitSudokuInformationVO;
import java.util.ArrayList;

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
  SudokuDataDTO generateSudokuTopic(SudokuLevel sudokuLevel, Boolean isRecord);

  /**
   * 获取提示信息
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 提示信息
   */
  SudokuGridInformationDTO getHelp(ArrayList<ArrayList<Integer>> userMatrix);

  /**
   * 检查用户的数独数据
   *
   * @param userMatrix 用户的数独矩阵数据
   * @return 用户答题情况
   */
  SubmitSudokuInformationVO checkSudokuData(ArrayList<ArrayList<Integer>> userMatrix);

  /**
   * 是否记录游戏信息
   *
   * @return 记录返回true，不记录返回false
   */
  Boolean isRecordGameInformation();
}
