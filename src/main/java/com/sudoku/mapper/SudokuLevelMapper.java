package com.sudoku.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.model.entity.SudokuLevel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

public interface SudokuLevelMapper extends BaseMapper<SudokuLevel> {

  @Cacheable(value = "sudokuLevels", keyGenerator = "simpleKG")
  List<SudokuLevel> selectAll();

  @Cacheable(value = "sudokuLevelIdToNameMap", keyGenerator = "simpleKG")
  default Map<Integer, String> selectIdToName() {
    List<SudokuLevel> sudokuLevels = selectAll();
    return sudokuLevels.stream().collect(Collectors.toMap(SudokuLevel::getId, SudokuLevel::getName, (a, b) -> b, HashMap::new));
  }

  @Cacheable(cacheNames = "sudokuLevelByLevel", keyGenerator = "simpleKG")
  SudokuLevel selectByLevel(@Param("level") Integer level);
}