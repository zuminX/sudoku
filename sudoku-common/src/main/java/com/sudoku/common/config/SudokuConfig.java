package com.sudoku.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数独项目相关配置
 */
@Component
@ConfigurationProperties(prefix = "sudoku")
public class SudokuConfig {

  /**
   * 项目名称
   */
  @Getter
  @Setter
  private String name;

  /**
   * 获取头像上传目录名
   */
  public static String getAvatarDir() {
    return "avatar";
  }
}
