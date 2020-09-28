package com.sudoku.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.sudoku.project.mapper.SudokuLevelMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数独等级的工具类
 */
@Component
public class SudokuLevelUtils {

  @Autowired
  private SudokuLevelMapper sudokuLevelMapper;


  /**
   * 查找给定的ID列表中缺少的数独等级ID列表
   *
   * @param sudokuLeveIds 数独等级ID列表
   * @return 缺少的数独等级ID列表
   */
  public List<Integer> findLackSudokuLevelId(List<Integer> sudokuLeveIds) {
    List<Integer> allSudokuLevelIds = sudokuLevelMapper.selectId();
    return CollUtil.isEmpty(sudokuLeveIds) ? allSudokuLevelIds : sudokuLeveIds.stream()
        .filter(sudokuLevel -> !allSudokuLevelIds.contains(sudokuLevel))
        .collect(Collectors.toList());
  }

}
