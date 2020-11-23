package com.sudoku.common.utils.project;

import cn.hutool.core.collection.CollUtil;
import com.sudoku.project.mapper.SudokuLevelMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * 数独等级工具类
 */
@Component
public class SudokuLevelUtils {

  private final SudokuLevelMapper sudokuLevelMapper;

  public SudokuLevelUtils(SudokuLevelMapper sudokuLevelMapper) {
    this.sudokuLevelMapper = sudokuLevelMapper;
  }

  /**
   * 获取所有的数独等级ID
   *
   * @return 数独等级ID列表
   */
  public List<Integer> getIdList() {
    return sudokuLevelMapper.selectId();
  }

  /**
   * 查找除给定的ID列表外缺少的数独等级ID列表
   *
   * @param sudokuLeveIds 数独等级ID列表
   * @return 缺少的数独等级ID列表
   */
  public List<Integer> findLackId(List<Integer> sudokuLeveIds) {
    List<Integer> allSudokuLevelIds = sudokuLevelMapper.selectId();
    return CollUtil.isEmpty(sudokuLeveIds) ? allSudokuLevelIds : allSudokuLevelIds.stream()
        .filter(sudokuLevel -> !sudokuLeveIds.contains(sudokuLevel))
        .collect(Collectors.toList());
  }

  /**
   * 根据数独等级名称获取数独等级ID
   *
   * @return 数独等级ID
   */
  public Optional<Integer> getIdByName(String sudokuLevelName) {
    return sudokuLevelMapper.selectIdByName(sudokuLevelName);
  }
}
