package com.sudoku.project.service.impl;

import com.sudoku.project.mapper.StatisticsGameMapper;
import com.sudoku.project.service.StatisticsGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsGameServiceImpl implements StatisticsGameService {

  @Autowired
  private StatisticsGameMapper statisticsGameMapper;


}
