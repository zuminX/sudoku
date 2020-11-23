package com.sudoku.project.service.impl;

import com.sudoku.project.mapper.RaceRecordMapper;
import com.sudoku.project.service.RaceRecordService;
import org.springframework.stereotype.Service;

/**
 * 竞赛记录业务层实现类
 */
@Service
public class RaceRecordServiceImpl implements RaceRecordService {

  private final RaceRecordMapper raceRecordMapper;

  public RaceRecordServiceImpl(RaceRecordMapper raceRecordMapper) {
    this.raceRecordMapper = raceRecordMapper;
  }


}
