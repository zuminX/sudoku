package com.sudoku.project.convert;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.project.model.bo.SubmitSudokuInformationBO;
import com.sudoku.project.model.vo.UserAnswerInformationVO;
import org.mapstruct.Mapper;

/**
 * 提交数独信息转换器
 */
@Mapper
public interface SubmitSudokuInformationConvert {

  /**
   * 将提交数独信息传输层对象转换为显示层对象
   *
   * @param information 提交数独信息传输层对象
   * @return 提交数独信息显示层对象
   */
  UserAnswerInformationVO convert(SubmitSudokuInformationBO information);

  /**
   * 自定义答题情况属性的转换
   *
   * @param answerSituation 答题情况
   * @return 答题情况的编号
   */
  default int answerSituationToInt(AnswerSituation answerSituation) {
    return answerSituation.getCode();
  }
}
