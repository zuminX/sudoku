package com.sudoku.framework.handler;

import com.sudoku.common.constant.enums.StatisticsDate;
import com.sudoku.common.core.domain.CustomEditorInfo;
import com.sudoku.common.core.handler.DataBindingHandler;
import java.util.Collections;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 全局数据绑定处理器类
 */
@ControllerAdvice
public class GlobalDataBindingHandler implements DataBindingHandler {

  /**
   * 初始化空的绑定信息
   *
   * @param binder 绑定器对象
   */
  @Override
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    initBinder(binder, Collections.singletonList(getStatisticsDateEditor()));
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