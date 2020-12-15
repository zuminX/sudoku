package com.sudoku.project.service;

import com.sudoku.project.mapper.RaceRecordMapper;
import org.springframework.stereotype.Service;

/**
 * 竞赛记录业务层类
 */
@Service
public class RaceRecordService {

  private final RaceRecordMapper raceRecordMapper;

  public RaceRecordService(RaceRecordMapper raceRecordMapper) {
    this.raceRecordMapper = raceRecordMapper;
  }


}
