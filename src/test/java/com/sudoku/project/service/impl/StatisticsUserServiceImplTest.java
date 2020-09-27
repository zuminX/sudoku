package com.sudoku.project.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.sudoku.SudokuApplication;
import com.sudoku.common.tools.RedisUtils;
import com.sudoku.project.service.StatisticsUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SudokuApplication.class)
class StatisticsUserServiceImplTest {

  @Autowired
  private StatisticsUserService statisticsUserService;

  @Test
  void getStatisticsUserData() {
  }

  @Test
  void getUserTotal() {
    System.out.println(statisticsUserService.getUserTotal());
  }

  @Test
  void getRedisUtils() {
    System.out.println(SpringUtil.getBean(RedisUtils.class));
  }

  @Test
  void updateDailyDataUntilNow() {
  }

  @Test
  void updateEachMonthDataUntilNow() {
  }
}