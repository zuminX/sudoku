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

  List<RaceInformationVO> selectAllByEndTimeBefore(@Param("maxEndTime") LocalDateTime maxEndTime);
}