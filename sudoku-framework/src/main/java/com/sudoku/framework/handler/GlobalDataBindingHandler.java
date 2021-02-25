package com.sudoku.framework.handler;

import com.sudoku.common.core.handler.DataBindingHandler;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.ArrayList;

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
    initBinder(binder, new ArrayList<>());
  }

}