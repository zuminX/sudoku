package com.sudoku.project.service;

import com.sudoku.project.mapper.RaceGameRecordMapper;
import org.springframework.stereotype.Service;

/**
 * 竞赛记录业务层类
 */
@Service
public class RaceRecordService {

  private final RaceGameRecordMapper raceGameRecordMapper;

  public RaceRecordService(RaceGameRecordMapper raceGameRecordMapper) {
    this.raceGameRecordMapper = raceGameRecordMapper;
  }


}
