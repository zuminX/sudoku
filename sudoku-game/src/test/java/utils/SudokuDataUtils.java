package utils;

import com.sudoku.game.model.bo.SudokuDataBO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数独数据工具类
 */
public class SudokuDataUtils {

  private SudokuDataUtils() {
  }

  public static SudokuDataBO getSudokuData() {
    return new SudokuDataBO(getMatrix(), getHoles());
  }

  public static int[][] getMatrix() {
    return new int[][]{
        {4, 3, 8, 7, 9, 6, 2, 1, 5},
        {6, 5, 9, 1, 3, 2, 4, 7, 8},
        {2, 7, 1, 4, 5, 8, 6, 9, 3},
        {8, 4, 5, 2, 1, 9, 3, 6, 7},
        {7, 1, 3, 5, 6, 4, 8, 2, 9},
        {9, 2, 6, 8, 7, 3, 1, 5, 4},
        {1, 9, 4, 3, 2, 5, 7, 8, 6},
        {3, 6, 2, 9, 8, 7, 5, 4, 1},
        {5, 8, 7, 6, 4, 1, 9, 3, 2}};
  }

  public static List<List<Integer>> getMatrixList() {
    List<List<Integer>> matrix = new ArrayList<>(9);
    matrix.addAll(Arrays.asList(
        Arrays.asList(4, 3, 8, 7, 9, 6, 2, 1, 5),
        Arrays.asList(6, 5, 9, 1, 3, 2, 4, 7, 8),
        Arrays.asList(2, 7, 1, 4, 5, 8, 6, 9, 3),
        Arrays.asList(8, 4, 5, 2, 1, 9, 3, 6, 7),
        Arrays.asList(7, 1, 3, 5, 6, 4, 8, 2, 9),
        Arrays.asList(9, 2, 6, 8, 7, 3, 1, 5, 4),
        Arrays.asList(1, 9, 4, 3, 2, 5, 7, 8, 6),
        Arrays.asList(3, 6, 2, 9, 8, 7, 5, 4, 1),
        Arrays.asList(5, 8, 7, 6, 4, 1, 9, 3, 2)));
    return matrix;
  }

  public static boolean[][] getHoles() {
    return new boolean[][]{
        {true, true, true, true, true, false, true, true, true},
        {true, false, false, true, true, true, true, true, false},
        {false, true, true, true, true, false, true, true, true},
        {true, false, false, true, true, true, true, true, true},
        {true, true, false, true, true, true, true, true, true},
        {true, true, false, true, true, false, true, false, false},
        {true, true, true, false, false, false, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true}};
  }

  public static int[][] getTopicMatrix() {
    return new int[][]{
        {0, 0, 0, 0, 0, 6, 0, 0, 0},
        {0, 5, 9, 0, 0, 0, 0, 0, 8},
        {2, 0, 0, 0, 0, 8, 0, 0, 0},
        {0, 4, 5, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 0, 0, 0, 0, 0, 0},
        {0, 0, 6, 0, 0, 3, 0, 5, 4},
        {0, 0, 0, 3, 2, 5, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}};
  }
}
