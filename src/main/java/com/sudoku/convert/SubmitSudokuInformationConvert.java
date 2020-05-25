package com.sudoku.convert;

import com.sudoku.constant.enums.AnswerSituation;
import com.sudoku.model.dto.SubmitSudokuInformationDTO;
import com.sudoku.model.vo.SubmitSudokuInformationVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 提交数独信息转换器
 */
@Mapper
public interface SubmitSudokuInformationConvert {

  SubmitSudokuInformationConvert INSTANCE = Mappers.getMapper(SubmitSudokuInformationConvert.class);

  /**
   * 将提交数独信息传输层对象转换为显示层对象
   *
   * @param information 提交数独信息传输层对象
   * @return 提交数独信息显示层对象
   */
  SubmitSudokuInformationVO convert(SubmitSudokuInformationDTO information);

  /**
   * 自定义答题情况属性的转换
   *
   * @param answerSituation 答题情况
   * @return 答题情况的编号
   */
  default int AnswerSituationToInt(AnswerSituation answerSituation) {
    return answerSituation.getCode();
  }
}
