package com.sudoku.project.service;

import com.sudoku.project.model.body.RaceInformationBody;
import com.sudoku.project.model.entity.RaceInformation;
import com.sudoku.project.model.vo.RaceInformationVO;
import java.util.List;

/**
 * 竞赛信息业务层接口
 */
public interface RaceInformationService {

  /**
   * 新增公开的竞赛
   *
   * @param raceInformationBody 竞赛内容信息对象
   * @return 竞赛信息
   */
  RaceInformation addPublicRace(RaceInformationBody raceInformationBody);

  /**
   * 将竞赛信息缓存至Redis中
   *
   * @param raceInformation 竞赛信息
   */
  void cacheRaceInformation(RaceInformation raceInformation);

  /**
   * 获取公开的数独游戏竞赛信息
   *
   * @return 竞赛信息显示层对象
   */
  List<RaceInformationVO> getPublicRaceList();

  /**
   * 移除过期的竞赛信息缓存
   * <p>
   * 将结束时间超过24小时的竞赛信息移除
   */
  void removeCacheExpiredRaceInformation();
}
