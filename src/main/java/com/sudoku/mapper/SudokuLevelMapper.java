package com.sudoku.mapper;

import com.sudoku.model.po.SudokuLevel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * 角色等级持久层
 */
public interface SudokuLevelMapper {

  int deleteByPrimaryKey(Integer id);

  int insert(SudokuLevel record);

  int insertSelective(SudokuLevel record);

  SudokuLevel selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(SudokuLevel record);

  int updateByPrimaryKey(SudokuLevel record);

  @Cacheable(cacheNames = "sudokuLevels")
  List<SudokuLevel> selectAll();

  @Cacheable(cacheNames = "sudokuLevelIdToNameMap")
  default Map<Integer, String> selectIdToName() {
    List<SudokuLevel> sudokuLevels = selectAll();
    return sudokuLevels.stream().collect(Collectors.toMap(SudokuLevel::getId, SudokuLevel::getName, (a, b) -> b, HashMap::new));
  }

  @Cacheable(cacheNames = "sudokuLevel", key = "#level")
  SudokuLevel selectByLevel(@Param("level") Integer level);
}