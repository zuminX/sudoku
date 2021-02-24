package com.sudoku.game.service;

import com.sudoku.common.constant.consist.SettingParameter;
import com.sudoku.common.constant.enums.StatusCode;
import com.sudoku.common.exception.RaceException;
import com.sudoku.common.tools.DateTimeRange;
import com.sudoku.game.convert.RaceInformationConvert;
import com.sudoku.game.convert.SudokuRecordConvert;
import com.sudoku.game.mapper.RaceInformationMapper;
import com.sudoku.game.mapper.SudokuRecordMapper;
import com.sudoku.game.model.body.RaceInformationBody;
import com.sudoku.game.model.entity.SudokuRecord;
import com.sudoku.game.model.vo.RaceInformationVO;
import com.sudoku.game.utils.sudoku.SudokuUtils;
import com.sudoku.system.utils.SecurityUtils;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 竞赛信息业务层类
 */
@Service
public class RaceInformationService {

  private final RaceInformationMapper raceInformationMapper;

  private final SudokuRecordMapper sudokuRecordMapper;

  private final RaceInformationConvert raceInformationConvert;

  private final SudokuRecordConvert sudokuRecordConvert;

  public RaceInformationService(RaceInformationMapper raceInformationMapper, RaceInformationConvert raceInformationConvert,
      SudokuRecordMapper sudokuRecordMapper, SudokuRecordConvert sudokuRecordConvert) {
    this.raceInformationMapper = raceInformationMapper;
    this.raceInformationConvert = raceInformationConvert;
    this.sudokuRecordMapper = sudokuRecordMapper;
    this.sudokuRecordConvert = sudokuRecordConvert;
  }

  /**
   * 新增公开的竞赛
   *
   * @param raceInformationBody 竞赛内容信息对象
   */
  @Transactional
  public void addPublicRace(RaceInformationBody raceInformationBody) {
    checkSudokuMatrix(raceInformationBody.getMatrix());
    checkSudokuHoles(raceInformationBody.getHoles());
    checkRaceTime(raceInformationBody.getRaceTimeRange());

    SudokuRecord sudokuRecord = sudokuRecordConvert.convert(raceInformationBody);
    sudokuRecordMapper.insert(sudokuRecord);

    raceInformationMapper.insert(
        raceInformationConvert.convert(raceInformationBody, sudokuRecord.getId(), SecurityUtils.getCurrentUserId()));
  }

  /**
   * 获取公开的数独游戏竞赛信息
   *
   * @return 竞赛信息显示层对象
   */
  public List<RaceInformationVO> getPublicRaceList() {
    return raceInformationMapper.selectAllByEndTimeBefore(LocalDateTime.now().plusDays(1L));
  }

  /**
   * 检查竞赛时间
   * <p>
   * 限制竞赛的时长至少为MINIMUM_RACE_DURATION，且距离竞赛的结束时间也至少为MINIMUM_RACE_DURATION
   *
   * @param raceTimeRange 竞赛时间范围
   */
  private void checkRaceTime(DateTimeRange raceTimeRange) {
    LocalDateTime start = raceTimeRange.getStart(), end = raceTimeRange.getEnd();
    if (start.plus(SettingParameter.MINIMUM_RACE_DURATION).compareTo(end) > 0
        || LocalDateTime.now().plus(SettingParameter.MINIMUM_RACE_DURATION).compareTo(end) > 0) {
      throw new RaceException(StatusCode.RACE_DURATION_TOO_SHORT);
    }
  }

  /**
   * 校验题目空缺数组的合法性
   *
   * @param holes 题目空缺数组
   */
  private void checkSudokuHoles(List<List<Boolean>> holes) {
    holes.stream().flatMap(Collection::stream).filter(Objects::isNull).forEach(aBoolean -> {
      throw new RaceException(StatusCode.RACE_SUDOKU_HOLES_Illegal);
    });
  }

  /**
   * 校验数独矩阵的合法性
   *
   * @param matrix 数独矩阵
   */
  private void checkSudokuMatrix(List<List<Integer>> matrix) {
    if (!SudokuUtils.checkSudokuValidity(matrix)) {
      throw new RaceException(StatusCode.RACE_SUDOKU_MATRIX_Illegal);
    }
  }
}
