package com.sudoku.common.utils;

import cn.hutool.core.util.ClassUtil;
import java.util.Set;

/**
 * 核心工具类
 */
public class CoreUtils {

  public static final String PROJECT_PACKAGE_NAME = "com.sudoku";

  /**
   * 私有构造方法，防止实例化
   */
  private CoreUtils() {
  }

  /**
   * 获取项目的所有Class
   *
   * @return Class集合
   */
  public static Set<Class<?>> getClasses() {
    return ClassUtil.scanPackage(PROJECT_PACKAGE_NAME);
  }
}
