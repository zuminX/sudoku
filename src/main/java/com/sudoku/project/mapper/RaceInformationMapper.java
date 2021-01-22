package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.RaceInformation;
import com.sudoku.project.model.vo.RaceInformationVO;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 竞赛信息持久层类
 */
public interface RaceInformationMapper extends BaseMapper<RaceInformation> {

  /**
   * 查询在指定结束日期时间前的竞赛信息
   *
   * @param maxEndTime 最大的结束日期时间
   * @return 竞赛信息列表
   */
  List<RaceInformationVO> selectAllByEndTimeBefore(@Param("maxEndTime") LocalDateTime maxEndTime);
}