package com.sudoku.game.controller;

import com.sudoku.common.core.domain.CustomEditorInfo;
import com.sudoku.common.core.handler.DataBindingHandler;
import com.sudoku.game.enums.RankingType;
import com.sudoku.game.model.entity.SudokuLevel;
import com.sudoku.game.service.SudokuLevelService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 游戏模块的基础控制器类
 */
@Controller
public class GameBaseController implements DataBindingHandler {

  @Autowired
  private SudokuLevelService sudokuLevelService;

  /**
   * 初始化游戏模块的绑定信息
   *
   * @param binder 绑定器对象
   */
  @Override
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    initBinder(binder, getCustomEditorInfoList());
  }

  /**
   * 设置游戏模块的自定义编辑信息列表
   *
   * @return 自定义编辑信息对象列表
   */
  private List<CustomEditorInfo<?>> getCustomEditorInfoList() {
    List<CustomEditorInfo<?>> customEditorInfoList = new ArrayList<>();
    customEditorInfoList.add(getSudokuLevelEditor());
    customEditorInfoList.add(getRankingTypeEditor());
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

}
