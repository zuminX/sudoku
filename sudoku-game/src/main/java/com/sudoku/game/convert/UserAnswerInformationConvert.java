package com.sudoku.game.convert;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.game.model.bo.UserAnswerInformationBO;
import com.sudoku.game.model.vo.UserAnswerInformationVO;
import org.mapstruct.Mapper;

/**
 * 用户答题情况对象转换器
 */
@Mapper
public interface UserAnswerInformationConvert {

  /**
   * 将用户答题情况业务层对象转换为显示层对象
   *
   * @param information 用户答题情况业务层对象
   * @return 用户答题情况显示层对象
   */
  UserAnswerInformationVO convert(UserAnswerInformationBO information);

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
