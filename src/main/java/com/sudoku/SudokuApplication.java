package com.sudoku;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * 数独游戏服务器的启动类
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.sudoku.project.mapper")
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class SudokuApplication {

  /**
   * 启动该服务器
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    SpringApplication.run(SudokuApplication.class, args);
  }
}
