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
   * 上传路径
   */
  private static String profile;

  /**
   * 项目名称
   */
  @Getter
  @Setter
  private String name;

  public static String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    SudokuConfig.profile = profile;
  }

  /**
   * 获取头像上传路径
   */
  public static String getAvatarPath() {
    return getProfile() + "/avatar";
  }
}
