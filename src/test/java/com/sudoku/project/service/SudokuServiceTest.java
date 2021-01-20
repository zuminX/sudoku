package com.sudoku.project.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.sudoku.common.constant.enums.AnswerSituation;
import com.sudoku.common.utils.SecurityUtils;
import com.sudoku.common.utils.sudoku.GameUtils;
import com.sudoku.common.utils.sudoku.SudokuBuilder;
import com.sudoku.project.convert.UserAnswerInformationConvert;
import com.sudoku.project.model.bo.SudokuDataBO;
import com.sudoku.project.model.bo.SudokuGridInformationBO;
import com.sudoku.project.model.bo.UserAnswerInformationBO;
import com.sudoku.project.model.entity.SudokuLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import utils.GameRecordUtils;
import utils.SudokuDataUtils;

/**
 * 数独业务层类的测试类
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SudokuBuilder.class, SecurityUtils.class})
public class SudokuServiceTest {

  @Mock
  private GameUtils gameUtils;

  private SudokuService sudokuService;

  /**
   * 初始化测试数据
   */
  @Before
  public void setUp() {
    gameUtils = spy(gameUtils);
    sudokuService = new SudokuService(gameUtils, Mappers.getMapper(UserAnswerInformationConvert.class));

    mockStatic(SudokuBuilder.class);
    mockStatic(SecurityUtils.class);
  }

  /**
   * 测试生成数独题目
   */
  @Test
  public void testGenerateSudokuTopic() {
    when(SudokuBuilder.generateSudokuFinal(anyInt(), anyInt())).thenReturn(SudokuDataUtils.getSudokuData());
    SudokuLevel sudokuLevel = SudokuLevel.builder().id(1).level(1).minEmpty(30).maxEmpty(35).name("Test").build();
    SudokuDataBO expectedSudokuDataBO = new SudokuDataBO(SudokuDataUtils.getTopicMatrix(), SudokuDataUtils.getHoles());

    SudokuDataBO actualSudokuDataBO = sudokuService.generateSudokuTopic(sudokuLevel, true);
    assertEquals(expectedSudokuDataBO, actualSudokuDataBO);
  }

  /**
   * 当存在错误时，测试获取提示信息
   */
  @Test
  public void testGetHelpIfHasError() {
    GameRecordUtils.mockSudokuRecord(gameUtils);
    List<List<Integer>> matrixList = SudokuDataUtils.getMatrixList();
    List<SudokuGridInformationBO> errorGridList = new ArrayList<>(
        Arrays.asList(new SudokuGridInformationBO(0, 0, 1), new SudokuGridInformationBO(1, 1, 3),
            new SudokuGridInformationBO(3, 5, 5)));
    errorGridList.forEach(
        gridInformation -> matrixList.get(gridInformation.getRow()).set(gridInformation.getColumn(), gridInformation.getValue()));

    SudokuGridInformationBO findErrorGrid = sudokuService.getHelp(matrixList);
    assertNotNull(findErrorGrid);
    assertEquals(SudokuDataUtils.getMatrixList().get(findErrorGrid.getRow()).get(findErrorGrid.getColumn()).intValue(),
        findErrorGrid.getValue());
    verifyContainErrorGrid(errorGridList, findErrorGrid);
  }

  /**
   * 当不存在错误时，测试获取提示信息
   */
  @Test
  public void testGetHelpIfNotHasError() {
    GameRecordUtils.mockSudokuRecord(gameUtils);
    assertNull(sudokuService.getHelp(SudokuDataUtils.getMatrixList()));
  }

  /**
   * 当回答与答案一致时，测试检查用户的数独数据
   */
  @Test
  public void testCheckSudokuDataIfIdentical() {
    GameRecordUtils.mockSudokuRecord(gameUtils);
    verifyUserAnswerInformation(sudokuService.checkSudokuData(SudokuDataUtils.getMatrixList()), AnswerSituation.IDENTICAL);
  }

  /**
   * 当回答不完全时，测试检查用户的数独数据
   */
  @Test
  public void testCheckSudokuDataIfCorrect() {
    GameRecordUtils.mockSudokuRecord(gameUtils);
    List<List<Integer>> userMatrix = SudokuDataUtils.getMatrixList();
    userMatrix.get(8).set(8, null);

    verifyUserAnswerInformation(sudokuService.checkSudokuData(userMatrix), AnswerSituation.UNFINISHED);
  }

  /**
   * 当回答有误时，测试检查用户的数独数据
   */
  @Test
  public void testCheckSudokuDataIfError() {
    GameRecordUtils.mockSudokuRecord(gameUtils);
    List<List<Integer>> userMatrix = SudokuDataUtils.getMatrixList();
    userMatrix.get(8).set(8, 1);

    verifyUserAnswerInformation(sudokuService.checkSudokuData(userMatrix), AnswerSituation.ERROR);
  }

  /**
   * 验证用户的答题情况
   *
   * @param userAnswerInformation 用户答题情况
   * @param expectSituation       预期的答题结果
   */
  private void verifyUserAnswerInformation(UserAnswerInformationBO userAnswerInformation, AnswerSituation expectSituation) {
    assertArrayEquals(SudokuDataUtils.getMatrix(), userAnswerInformation.getMatrix());
    assertEquals(expectSituation, userAnswerInformation.getSituation());
  }

  /**
   * 验证找到的错误格子在错误格子信息列表中
   *
   * @param errorGridList 错误格子列表
   * @param findErrorGrid 找到的错误格子
   */
  private void verifyContainErrorGrid(List<SudokuGridInformationBO> errorGridList, SudokuGridInformationBO findErrorGrid) {
    for (SudokuGridInformationBO sudokuGridInformationBO : errorGridList) {
      if (sudokuGridInformationBO.getRow() == findErrorGrid.getRow() && sudokuGridInformationBO.getColumn() == findErrorGrid.getColumn()
          && sudokuGridInformationBO.getValue() != findErrorGrid.getValue()) {
        return;
      }
    }
    fail("找到的错误格子不在设定的错误格子信息列表中");
  }
}