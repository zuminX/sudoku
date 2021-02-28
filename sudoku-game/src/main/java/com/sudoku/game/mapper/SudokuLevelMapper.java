package com.sudoku.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.common.annotation.ExtCacheable;
import com.sudoku.game.model.entity.SudokuLevel;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

/**
 * 数独等级持久层类
 */
public interface SudokuLevelMapper extends BaseMapper<SudokuLevel> {

  /**
   * 查询所有的数独等级
   *
   * @return 数独等级列表
   */
  @ExtCacheable(value = "sudokuLevels")
  List<SudokuLevel> selectAll();

  /**
   * 根据数独等级名查询其ID
   *
   * @param name 数独等级名
   * @return 数独等级名对应的ID
   */
  @ExtCacheable(value = "sudokuIdByName")
  Optional<Integer> selectIdByName(@Param("name") String name);

  /**
   * 查询所有的数独等级ID
   *
   * @return 数独等级ID列表
   */
  @ExtCacheable(value = "sudokuLevelIds")
  List<Integer> selectId();

  /**
   * 根据数独等级值查询数独等级
   *
   * @param level 数独等级值
   * @return 数独等级
   */
  @ExtCacheable(value = "sudokuLevelByLevel")
  SudokuLevel selectByLevel(@Param("level") Integer level);
}