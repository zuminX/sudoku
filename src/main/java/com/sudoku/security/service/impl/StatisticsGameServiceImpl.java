package com.sudoku.security.service.impl;

import com.sudoku.mapper.StatisticsGameMapper;
import com.sudoku.security.service.StatisticsGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsGameServiceImpl implements StatisticsGameService {

  @Autowired
  private StatisticsGameMapper statisticsGameMapper;


}
