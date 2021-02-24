package com.sudoku.common.core.controller;

import com.sudoku.common.convert.StringConvert;
import com.sudoku.common.exception.FormParameterConversionException;
import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.PropertyValuesEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 基础控制器类
 */
@Controller
public class BaseController {

  /**
   * 获取自定义编辑信息列表
   *
   * @return 自定义编辑信息对象列表
   */
  public List<CustomEditorInfo<?>> getCustomEditorInfoList() {
    return new ArrayList<>();
  }

  /**
   * 初始化绑定信息
   */
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    getCustomEditorInfoList().forEach(
        customEditorInfo -> binder.registerCustomEditor(customEditorInfo.requiredType, customEditorInfo.propertyEditor));
  }

  /**
   * 自定义编辑信息类
   */
  public static class CustomEditorInfo<T> {

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
