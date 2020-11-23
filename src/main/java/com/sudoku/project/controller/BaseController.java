package com.sudoku.project.controller;

import com.sudoku.common.constant.enums.RankingType;
import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.exception.FormParameterConversionException;
import com.sudoku.project.convert.StringConvert;
import com.sudoku.project.model.entity.SudokuLevel;
import com.sudoku.project.service.SudokuLevelService;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.PropertyValuesEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 基础控制器类
 */
@Controller
public class BaseController {

  @Autowired
  private SudokuLevelService sudokuLevelService;

  /**
   * 将前台传递过来的数独难度级别的字符串，自动转化为对应的数独等级对象
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    List<CustomEditorInfo<?>> customEditorInfoList = new ArrayList<>();
    customEditorInfoList.add(getSudokuLevelEditor());
    customEditorInfoList.add(getRankingTypeEditor());
    customEditorInfoList.add(getStatisticsDateEditor());

    customEditorInfoList.forEach(
        customEditorInfo -> binder.registerCustomEditor(customEditorInfo.requiredType, customEditorInfo.propertyEditor));
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

  /**
   * 自定义编辑信息类
   */
  private static class CustomEditorInfo<T> {

    private final Class<T> requiredType;
    private final PropertyEditor propertyEditor;

    /**
     * 自定义编辑信息类的构造方法
     *
     * @param requiredType 需要的类型
     * @param callback     将字符串转换为需要类型对象的转换器
     */
    public CustomEditorInfo(Class<T> requiredType, StringConvert<T> callback) {
      this.requiredType = requiredType;
      this.propertyEditor = new PropertyValuesEditor() {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
          T data = callback.convert(text);
          if (data == null) {
            throw new FormParameterConversionException(text, requiredType);
          }
          setValue(data);
        }
      };
    }
  }

}
