package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.SudokuLevel;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

public interface SudokuLevelMapper extends BaseMapper<SudokuLevel> {

  @Cacheable(value = "sudokuLevels", keyGenerator = "simpleKG")
  List<SudokuLevel> selectAll();

  @Cacheable(value = "sudokuIdByName", keyGenerator = "simpleKG")
  Optional<Integer> selectIdByName(@Param("name")String name);

  @Cacheable(value = "sudokuLevelIds", keyGenerator = "simpleKG")
  List<Integer> selectId();

  @Cacheable(value = "sudokuLevelIdToNameMap", keyGenerator = "simpleKG")
  default Map<String, String> selectIdToName() {
    List<SudokuLevel> sudokuLevels = selectAll();
    //此处存在bug，redis返回的数据类型为Map<String,String>
    return sudokuLevels.stream()
                       .collect(
                           Collectors.toMap(sudokuLevel -> String.valueOf(sudokuLevel.getId()), SudokuLevel::getName,
                               (a, b) -> b));
  }

  @Cacheable(cacheNames = "sudokuLevelByLevel", keyGenerator = "simpleKG")
  SudokuLevel selectByLevel(@Param("level") Integer level);
}