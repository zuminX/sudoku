package com.sudoku;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 数独游戏服务器的启动类
 */
@EnableOpenApi
@SpringBootApplication
@EnableCaching
@MapperScan(basePackages = {"com.sudoku.game.mapper", "com.sudoku.system.mapper"})
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
