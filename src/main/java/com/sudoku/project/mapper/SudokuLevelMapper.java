package com.sudoku.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoku.project.model.entity.SudokuLevel;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

/**
 * 数独等级持久层类
 */
public interface SudokuLevelMapper extends BaseMapper<SudokuLevel> {

  /**
   * 查询所有的数独等级
   *
   * @return 数独等级列表
   */
  @Cacheable(value = "sudokuLevels", keyGenerator = "simpleKG")
  List<SudokuLevel> selectAll();

  /**
   * 根据数独等级名查询其ID
   *
   * @param name 数独等级名
   * @return 数独等级名对应的ID
   */
  @Cacheable(value = "sudokuIdByName", keyGenerator = "simpleKG")
  Optional<Integer> selectIdByName(@Param("name") String name);

  /**
   * 查询所有的数独等级ID
   *
   * @return 数独等级ID列表
   */
  @Cacheable(value = "sudokuLevelIds", keyGenerator = "simpleKG")
  List<Integer> selectId();

  /**
   * 获取数独等级ID到数独等级名的映射表
   *
   * @return 数独等级ID到数独等级名的映射表
   */
  @Cacheable(value = "sudokuLevelIdToNameMap", keyGenerator = "simpleKG")
  default Map<String, String> selectIdToName() {
    List<SudokuLevel> sudokuLevels = selectAll();
    //TODO 此处存在bug，redis返回的数据类型为Map<String,String>
    return sudokuLevels.stream()
        .collect(
            Collectors.toMap(sudokuLevel -> String.valueOf(sudokuLevel.getId()), SudokuLevel::getName,
                (a, b) -> b));
  }

  /**
   * 根据数独等级值查询数独等级
   *
   * @param level 数独等级值
   * @return 数独等级
   */
  @Cacheable(cacheNames = "sudokuLevelByLevel", keyGenerator = "simpleKG")
  SudokuLevel selectByLevel(@Param("level") Integer level);
}