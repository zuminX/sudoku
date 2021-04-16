package com.sudoku.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数独项目相关配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "sudoku")
public class SudokuConfig {

  /**
   * 获取头像上传目录名
   */
  public static String getAvatarDir() {
    return "avatar";
  }
}
