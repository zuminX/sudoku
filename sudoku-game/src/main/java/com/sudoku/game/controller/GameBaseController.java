package com.sudoku.game.controller;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.core.controller.BaseController;
import com.sudoku.game.enums.RankingType;
import com.sudoku.game.model.entity.SudokuLevel;
import com.sudoku.game.service.SudokuLevelService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 游戏模块的基础控制器类
 */
@Controller
public class GameBaseController extends BaseController {

  @Autowired
  private SudokuLevelService sudokuLevelService;

  /**
   * 设置游戏模块的自定义编辑信息列表
   *
   * @return 自定义编辑信息对象列表
   */
  @Override
  public List<CustomEditorInfo<?>> getCustomEditorInfoList() {
    List<CustomEditorInfo<?>> customEditorInfoList = new ArrayList<>();
    customEditorInfoList.add(getSudokuLevelEditor());
    customEditorInfoList.add(getRankingTypeEditor());
    customEditorInfoList.add(getStatisticsDateEditor());
    return customEditorInfoList;
  }

  /**
   * 获取数独等级的自定义编辑信息对象
   *
   * @return 数独等级的自定义编辑信息对象
   */
  private CustomEditorInfo<SudokuLevel> getSudokuLevelEditor() {
    return new CustomEditorInfo<>(SudokuLevel.class, text -> sudokuLevelService.getSudokuLevel(Integer.parseInt(text)));
  }

  /**
   * 获取排行类型的自定义编辑信息对象
   *
   * @return 排行类型的自定义编辑信息对象
   */
  private CustomEditorInfo<RankingType> getRankingTypeEditor() {
    return new CustomEditorInfo<>(RankingType.class, RankingType::findByName);
  }

  /**
   * 获取统计日期的自定义编辑信息对象
   *
   * @return 统计日期的自定义编辑信息对象
   */
  private CustomEditorInfo<StatisticsDate> getStatisticsDateEditor() {
    return new CustomEditorInfo<>(StatisticsDate.class, StatisticsDate::findByName);
  }

}
