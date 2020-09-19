package com.sudoku.framework.security.service.impl;

import com.sudoku.project.mapper.StatisticsGameMapper;
import com.sudoku.framework.security.service.StatisticsGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsGameServiceImpl implements StatisticsGameService {

  @Autowired
  private StatisticsGameMapper statisticsGameMapper;


}
