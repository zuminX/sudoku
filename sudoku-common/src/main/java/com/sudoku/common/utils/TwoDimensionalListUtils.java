package com.sudoku.common.utils;

import com.sudoku.common.tools.functional.QuadrConsumer;
import com.sudoku.common.tools.functional.TriConsumer;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * 二维列表工具类
 */
public class TwoDimensionalListUtils {

  /**
   * 私有构造方法，防止实例化
   */
  private TwoDimensionalListUtils() {
  }

  /**
   * 遍历二维列表
   *
   * @param list     二维列表
   * @param consumer 每个遍历项要执行的动作
   * @param <T>      元素类型
   */
  public static <T> void forEach(@NotNull T[][] list, @NotNull TriConsumer<Integer, Integer, T> consumer) {
    for (int i = 0; i < list.length; i++) {
      for (int j = 0; j < list[i].length; j++) {
        consumer.accept(i, j, list[i][j]);
      }
    }
  }

  /**
   * 遍历两个二维列表重叠的区域
   *
   * @param list1    第一个二维列表
   * @param list2    第二个二维列表
   * @param consumer 每个遍历项要执行的动作
   * @param <T>      第一个元素类型
   * @param <U>      第二个元素类型
   */
  public static <T, U> void forEach(@NotNull T[][] list1, @NotNull List<List<U>> list2,
      @NotNull QuadrConsumer<Integer, Integer, T, U> consumer) {
    int rowSize = Math.min(list1.length, list2.size());
    for (int i = 0; i < rowSize; i++) {
      int columnSize = Math.min(list1[i].length, list2.get(i).size());
      for (int j = 0; j < columnSize; j++) {
        consumer.accept(i, j, list1[i][j], list2.get(i).get(j));
      }
    }
  }

  /**
   * 遍历两个二维列表重叠的区域
   *
   * @param list1    第一个二维列表
   * @param list2    第二个二维列表
   * @param consumer 当两个遍历项不同时要执行的动作
   * @param <T>      第一个元素类型
   * @param <U>      第二个元素类型
   */
  public static <T, U> void forEachIfNotEquals(@NotNull T[][] list1, @NotNull List<List<U>> list2,
      @NotNull QuadrConsumer<Integer, Integer, T, U> consumer) {
    forEach(list1, list2, consumer.condition((i, j, t, u) -> t == null ? u == null : u != null && !t.equals(u)));
  }

  /**
   * 遍历两个二维列表重叠的区域
   *
   * @param list1    第一个二维列表
   * @param list2    第二个二维列表
   * @param consumer 当两个遍历项不同时要执行的动作
   */
  public static void forEachIfNotEquals(int[][] list1, @NotNull List<List<Integer>> list2,
      @NotNull QuadrConsumer<Integer, Integer, Integer, Integer> consumer) {
    forEachIfNotEquals(PublicUtils.wrap(list1), list2, consumer);
  }

}
