package com.sudoku.common.core.handler;

import com.sudoku.common.core.domain.CustomEditorInfo;
import org.springframework.web.bind.WebDataBinder;

import java.util.List;

/**
 * 数据绑定处理器接口
 */
public interface DataBindingHandler {

  /**
   * 初始化空的绑定信息
   *
   * @param binder 绑定器对象
   */
  void initBinder(WebDataBinder binder);

  /**
   * 初始化绑定信息
   *
   * @param binder               绑定器对象
   * @param customEditorInfoList 自定义编辑信息对象列表
   */
  default void initBinder(WebDataBinder binder, List<CustomEditorInfo<?>> customEditorInfoList) {
    customEditorInfoList.forEach(
        customEditorInfo -> binder.registerCustomEditor(customEditorInfo.getRequiredType(), customEditorInfo.getPropertyEditor()));
  }
}
