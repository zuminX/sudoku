package com.sudoku.common.utils;

import static cn.hutool.core.lang.Assert.checkBetween;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * 公共工具类的测试类
 */
@RunWith(MockitoJUnitRunner.class)
public class PublicUtilsTest {

  /**
   * 测试将二维Integer型List转为二维int数组
   */
  @Test
  public void testUnwrapIntArray() {
    List<List<Integer>> list = new ArrayList<>();
    list.add(null);
    list.add(Arrays.asList(6, 5, 9, 1, 3, 2, 4, 7, 8));
    list.add(Arrays.asList(2, 7, 1, 4, 5, 8, 6, 9, 3));
    list.add(Arrays.asList(null, null, null, null, null, null, null, null, null));
    list.add(Collections.emptyList());
    list.add(Arrays.asList(9, 2, 6, 8, 7, 3));
    list.add(Arrays.asList(1, 9, 4, 3, 2, 5, 7, 8, 6));
    list.add(Arrays.asList(3, 6, 2, 9, 8, 7, 5, 4, 1));
    list.add(Arrays.asList(5, 8, 7, 6, 4, 1, 9, 3, null));
    int[][] array = {
        {},
        {6, 5, 9, 1, 3, 2, 4, 7, 8},
        {2, 7, 1, 4, 5, 8, 6, 9, 3},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {},
        {9, 2, 6, 8, 7, 3},
        {1, 9, 4, 3, 2, 5, 7, 8, 6},
        {3, 6, 2, 9, 8, 7, 5, 4, 1},
        {5, 8, 7, 6, 4, 1, 9, 3, 0}};

    assertArrayEquals(array, PublicUtils.unwrapIntArray(list));
  }

  /**
   * 测试将空的二维Integer型List转为二维int数组
   */
  @Test
  public void testUnwrapIntArrayWithEmpty() {
    int[][] empty = new int[0][0];
    System.out.println("ClassUnderTest: " + PublicUtilsTest.class.getClassLoader());
    assertArrayEquals(empty, PublicUtils.unwrapIntArray(null));
    assertArrayEquals(empty, PublicUtils.unwrapIntArray(new ArrayList<>()));
  }

  /**
   * 测试将二维Boolean型List转为二维boolean数组
   */
  @Test
  public void testUnwrapBoolArray() {
    List<List<Boolean>> list = new ArrayList<>();
    list.add(null);
    list.add(Arrays.asList(true, false, false, true, true, true, true, true, false));
    list.add(Arrays.asList(false, true, true, true, true, false, true, true, true));
    list.add(Arrays.asList(null, null, null, null, null, null, null, null, null));
    list.add(Collections.emptyList());
    list.add(Arrays.asList(true, true, false, true, true, false, true, false, false));
    list.add(Arrays.asList(true, true, true, false, false, false, true, true, true));
    list.add(Arrays.asList(true, true, true, true, true, true, true, true, true));
    list.add(Arrays.asList(true, true, true, true, true, true, true, true, null));
    boolean[][] array = {
        {},
        {true, false, false, true, true, true, true, true, false},
        {false, true, true, true, true, false, true, true, true},
        {false, false, false, false, false, false, false, false, false},
        {},
        {true, true, false, true, true, false, true, false, false},
        {true, true, true, false, false, false, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, false}};

    assertArrayEquals(array, PublicUtils.unwrapBoolArray(list));
  }

  /**
   * 测试将空的二维Boolean型List转为二维boolean数组
   */
  @Test
  public void testUnwrapBoolArrayWithEmpty() {
    boolean[][] empty = new boolean[0][0];

    assertArrayEquals(empty, PublicUtils.unwrapBoolArray(null));
    assertArrayEquals(empty, PublicUtils.unwrapBoolArray(new ArrayList<>()));
  }

  /**
   * 测试获取[min,max]的整数
   */
  @Test
  public void getRandomInt() {
    int min = 0, max = 100;
    checkBetween(PublicUtils.getRandomInt(min, max), min, max);
  }

  /**
   * 测试当min>max时，获取[min,max]的整数
   */
  @Test(expected = IllegalArgumentException.class)
  public void getRandomIntWithMinGreaterMax() {
    int min = 100, max = 0;
    checkBetween(PublicUtils.getRandomInt(min, max), min, max);
  }

  /**
   * 测试深拷贝二维int型数组
   */
  @Test
  public void testClone() {
    int[][] source = new int[][]{{0xff, 0xfff, 0xffff}};
    int[][] clone = PublicUtils.deepClone(source);

    assertNotNull(clone);
    assertArrayEquals(source, clone);
    if (IntStream.range(0, source.length).anyMatch(i -> source[i] == clone[i])) {
      fail("拷贝的数据与源数据的地址相同");
    }
  }

  /**
   * 测试深拷贝为null的二维int型数组
   */
  @Test
  public void testCloneWithNull() {
    assertNull(PublicUtils.deepClone((int[][]) null));
  }

  /**
   * 测试获取两个日期时间的差值
   */
  @Test
  public void testComputeDiff() {
    LocalDateTime dateTime1 = LocalDateTime.of(2020, 1, 1, 10, 0, 0), dateTime2 = LocalDateTime.of(2020, 1, 1, 10, 10, 0);
    assertEquals(-600_000L, DateUtils.computeDiff(dateTime1, dateTime2));
  }

  /**
   * 测试获取两个日期时间的绝对差值
   */
  @Test
  public void testComputeAbsDiff() {
    LocalDateTime dateTime1 = LocalDateTime.of(2020, 1, 1, 10, 0, 0), dateTime2 = LocalDateTime.of(2020, 1, 1, 10, 10, 0);
    assertEquals(600_000L, DateUtils.computeAbsDiff(dateTime1, dateTime2));
  }

  /**
   * 测试压缩二维int型数组为字符串
   */
  @Test
  public void testCompressionIntArray() {
    int[][] matrix = {
        {4, 3, 8, 7, 9, 6, 2, 1, 5},
        {6, 5, 9, 1, 3, 2, 4, 7, 8},
        {2, 7, 1, 4, 5, 8, 6, 9, 3},
        {8, 4, 5, 2, 1, 9, 3, 6, 7},
        {7, 1, 3, 5, 6, 4, 8, 2, 9},
        {9, 2, 6, 8, 7, 3, 1, 5, 4},
        {1, 9, 4, 3, 2, 5, 7, 8, 6},
        {3, 6, 2, 9, 8, 7, 5, 4, 1},
        {5, 8, 7, 6, 4, 1, 9, 3, 2}};
    String sudokuMatrix = "438796215659132478271458693845219367713564829926873154194325786362987541587641932";

    assertEquals(sudokuMatrix, PublicUtils.compressionIntArray(matrix));
  }

  /**
   * 测试压缩空的二维int型数组为字符串
   */
  @Test
  public void testCompressionIntArrayWithEmpty() {
    assertEquals("", PublicUtils.compressionIntArray(null));
    assertEquals("", PublicUtils.compressionIntArray(new int[0][0]));
  }

  /**
   * 测试压缩二维boolean型数组为字符串
   */
  @Test
  public void testCompressionBoolArray() {
    boolean[][] holes = {
        {true, true, true, true, true, false, true, true, true},
        {true, false, false, true, true, true, true, true, false},
        {false, true, true, true, true, false, true, true, true},
        {true, false, false, true, true, true, true, true, true},
        {true, true, false, true, true, true, true, true, true},
        {true, true, false, true, true, false, true, false, false},
        {true, true, true, false, false, false, true, true, true},
        {true, true, true, true, true, true, true, true, true},
        {true, true, true, true, true, true, true, true, true}};
    String sudokuHoles = "111110111100111110011110111100111111110111111110110100111000111111111111111111111";

    assertEquals(sudokuHoles, PublicUtils.compressionBoolArray(holes));
  }

  /**
   * 测试压缩空的二维boolean型数组为字符串
   */
  @Test
  public void testCompressionBoolArrayWithEmpty() {
    assertEquals("", PublicUtils.compressionBoolArray(null));
    assertEquals("", PublicUtils.compressionBoolArray(new boolean[0][0]));
  }

}